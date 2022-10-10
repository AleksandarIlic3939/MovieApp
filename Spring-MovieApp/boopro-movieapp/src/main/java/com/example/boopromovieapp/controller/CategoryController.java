package com.example.boopromovieapp.controller;

import com.example.boopromovieapp.models.Category;
import com.example.boopromovieapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    /*
        REST endpoints
    */

    @GetMapping("/user/categories")
    @ResponseBody
    public ResponseEntity<Page<Category>> getAll(Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }

    @RequestMapping(value = "/admin/categories/findCategoryById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findCategoryById(Integer id) {
        return categoryService.getById(id);
    }

    /*
        MVC endpoints
    */

    @GetMapping("/admin/categories/{pageNo}")
    public String categories(Model model, Principal principal, @PathVariable Integer pageNo) {
//        if (principal == null) {
//            return "redirect:/login";
//        }
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Category> categories = categoryService.getAll(pageable);
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.getSize());
        model.addAttribute("title", "Category");
        model.addAttribute("totalPage", categories.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @PostMapping("/admin/categories/add-category")
    public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes) {
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Added successfully!");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add due to existing name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/categories/0";
    }

    @GetMapping("/admin/categories/update-category")
    public String update(Category category, RedirectAttributes attributes) {
        try {
            categoryService.save(category);
            attributes.addFlashAttribute("success", "Updated successfully!");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add due to existing name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed!");
        }
        return "redirect:/admin/categories/0";
    }

    @RequestMapping(value = "/admin/categories/delete-category", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id, RedirectAttributes attributes) {
        try {
            categoryService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed with deleting");
        }
        return "redirect:/admin/categories/0";
    }

}