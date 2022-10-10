package com.example.boopromovieapp.controller;

import com.example.boopromovieapp.controller.dtos.MovieDTO;
import com.example.boopromovieapp.controller.dtos.UserDTO;
import com.example.boopromovieapp.models.Category;
import com.example.boopromovieapp.models.Movie;
import com.example.boopromovieapp.models.Role;
import com.example.boopromovieapp.models.User;
import com.example.boopromovieapp.service.RoleService;
import com.example.boopromovieapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    /*
        Rest endspoints
    */

    @PostMapping("/users/create")
    @ResponseBody
    public void saveUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
    }

    /*
        MVC endpoints
    */

    @GetMapping("/admin/users/{pageNo}")
    public String usersPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
//        if (principal == null) {
//            return "redirect:/login";
//        }
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<User> users = userService.getAll(pageable);
        model.addAttribute("users", users);
        model.addAttribute("size", users.getSize());
        model.addAttribute("title", "Users");
        model.addAttribute("totalPage", users.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "users";
    }

    @GetMapping("/admin/users/add-user")
    public String addUserForm(Model model, Principal principal) {
//        if (principal == null) {
//            return "redirect:/login";
//        }

        List<Role> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new UserDTO());
        return "add-user";
    }

    @PostMapping("/admin/users/save-user")
    public String save(@ModelAttribute("user") UserDTO userDTO,
                       RedirectAttributes attributes) {
        try {
            if (userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
                userService.save(userDTO);
                attributes.addFlashAttribute("success", "Added successfully!");
            } else {
                attributes.addFlashAttribute("failed", "Failed to add! Passwords must match!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add!");
        }
        return "redirect:/admin/users/0";
    }

    @GetMapping("/admin/users/update-user/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model, Principal principal) {
//        if (principal == null) {
//            return "redirect:/login";
//        }
        model.addAttribute("title", "Update movie");
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/admin/users/update-user/{id}")
    public String processUpdate(@PathVariable("id") Integer id,
                                @ModelAttribute("user") User user,
                                RedirectAttributes attributes) {
        try {
            userService.update(user);
            attributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/users/0";
    }

    @RequestMapping(value = "/admin/users/delete-user", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id, RedirectAttributes attributes) {
        try {
            userService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed with deleting");
        }
        return "redirect:/admin/users/0";
    }

}