package com.example.boopromovieapp.controller;

import com.example.boopromovieapp.controller.dtos.MovieDTO;
import com.example.boopromovieapp.models.Category;
import com.example.boopromovieapp.models.Movie;
import com.example.boopromovieapp.service.CategoryService;
import com.example.boopromovieapp.service.MovieService;
import lombok.RequiredArgsConstructor;
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
public class MovieController {

    private final MovieService movieService;
    private final CategoryService categoryService;

    /*
        REST endpoints
    */

    @GetMapping(value ="/user/movies", params = "categoryId")
    @ResponseBody
    public ResponseEntity<Page<Movie>> getAllByCategory(@RequestParam Integer categoryId, Pageable pageable) {
        return ResponseEntity.ok(movieService.getAllByCategory(categoryId, pageable));
    }

    @GetMapping("/user/movies/{movieId}")
    @ResponseBody
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer movieId) {
        return ResponseEntity.ok(movieService.getMovieById(movieId));
    }

    /*
        MVC endpoints
    */

    @GetMapping("/admin/movies/{pageNo}")
    public String moviesPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Movie> movies = movieService.findAll(pageable);
        model.addAttribute("movies", movies);
        model.addAttribute("size", movies.getSize());
        model.addAttribute("title", "Movies");
        model.addAttribute("totalPage", movies.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "movies";
    }

    @GetMapping("/admin/movies/add-movie")
    public String addMovieForm(Model model, Principal principal) {
//        if (principal == null) {
//            return "redirect:/login";
//        }

        Page<Category> categories = categoryService.getAll(Pageable.unpaged());
        model.addAttribute("categories", categories);
        model.addAttribute("movie", new MovieDTO());
        return "add-movie";
    }

    @PostMapping("/admin/movies/save-movie")
    public String save(@ModelAttribute("movie") MovieDTO movieDTO,
                       RedirectAttributes attributes) {
        try {
            movieService.save(movieDTO);
            attributes.addFlashAttribute("success", "Add successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to add!");
        }
        return "redirect:/admin/movies/0";
    }

    @GetMapping("/admin/movies/update-movie/{id}")
    public String updateMovieForm(@PathVariable("id") Integer id, Model model, Principal principal) {
//        if (principal == null) {
//            return "redirect:/login";
//        }
        model.addAttribute("title", "Update movie");
        Page<Category> categories = categoryService.getAll(Pageable.unpaged());
        model.addAttribute("categories", categories);
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @PostMapping("/admin/movies/update-movie/{id}")
    public String processUpdate(@PathVariable("id") Integer id,
                                @ModelAttribute("movieDTO") Movie movie,
                                RedirectAttributes attributes) {
        try {
            movieService.update(movie);
            attributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/admin/movies/0";
    }

    @RequestMapping(value = "/admin/movies/delete-movie", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id, RedirectAttributes attributes) {
        try {
            movieService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed with deleting");
        }
        return "redirect:/admin/movies/0";
    }

}