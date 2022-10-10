package com.example.boopromovieapp.repository.seeders;

import com.example.boopromovieapp.models.Category;
import com.example.boopromovieapp.models.Movie;
import com.example.boopromovieapp.models.Role;
import com.example.boopromovieapp.models.User;
import com.example.boopromovieapp.models.helpers.Genres;
import com.example.boopromovieapp.models.helpers.MovieSummaries;
import com.example.boopromovieapp.models.helpers.MovieSummary;
import com.example.boopromovieapp.repository.CategoryRepository;
import com.example.boopromovieapp.repository.MovieRepository;
import com.example.boopromovieapp.repository.RoleRepository;
import com.example.boopromovieapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;

    private String apiKey = "e7110aa5c8d96245b4760f7ce261f52d";

    @Override
    public void run(String... args) throws Exception {
        this.loadRoleData();
        this.loadUserData();
        this.loadMovieData();
    }

    private void loadUserData() {
        if(userRepository.count() == 0) {
            List<User> users = Arrays.asList(
                    new User(1, "admin@boopro.tech", "admin", new Role(1, "ADMIN")),
                    new User(2, "example@boopro.tech", "123123", new Role(2, "USER")),
                    new User(3, "user@boopro.tech", "456465", new Role(2, "USER"))
            );
            userRepository.saveAll(users);
        }
    }

    private void loadRoleData() {
        if(roleRepository.count() == 0) {
            List<Role> roles = Arrays.asList(
                    new Role(1, "ADMIN"),
                    new Role(2, "USER")
            );
            roleRepository.saveAll(roles);
        }
    }

    private void loadMovieData() {
        RestTemplate restTemplate = new RestTemplate();
        if(movieRepository.count() == 0) {
            MovieSummaries movieSummaries = restTemplate.getForObject("https://api.themoviedb.org/3/movie/popular"
                    + "?api_key="
                    + apiKey, MovieSummaries.class);
            Genres genres = restTemplate.getForObject("https://api.themoviedb.org/3/genre/movie/list?api_key="
                    + apiKey, Genres.class);

            if(categoryRepository.count() == 0) {
                categoryRepository.saveAll(Arrays.asList(genres.getGenres()));
            }

            List<Movie> movies = new ArrayList<>();

            if(movieRepository.count() == 0) {
                for (MovieSummary movieSummary : movieSummaries.getMovieSummaries()) {
                    Category category = null;
                    for (Category c : genres.getGenres()) {
                        if (c.getId().intValue() == movieSummary.getGenreIds()[0].intValue()) {
                            category = c;
                            break;
                        }
                    }
                    movies.add(new Movie(
                            movieSummary.getId(),
                            movieSummary.getTitle(),
                            "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movieSummary.getPosterPath(),
                            categoryRepository.findCategoryByName(category.getName())
                    ));
                }

                movieRepository.saveAll(movies);
            }

        }
    }

}