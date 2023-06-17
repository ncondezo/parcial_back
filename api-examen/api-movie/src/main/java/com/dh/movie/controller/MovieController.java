package com.dh.movie.controller;

import com.dh.movie.model.Movie;
import com.dh.movie.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {


    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{genre}")
    ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre) {
        return ResponseEntity.ok().body(movieService.findByGenre(genre));
    }

    /*@PostMapping("/save")
    ResponseEntity<Long> saveMovie(@RequestBody Movie movie) {
        //return ResponseEntity.ok().body(movieService.save(movie));
        movieService.save(movie);
        return ResponseEntity.ok(movie.getId());
    }*/
    @PostMapping("/save")
    public ResponseEntity<Long>saveMovie(@RequestBody Movie movie){
        Long movieId = movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieId);
    }
}
