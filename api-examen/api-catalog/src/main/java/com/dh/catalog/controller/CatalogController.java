package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;

import com.dh.catalog.client.SerieServiceClient;

import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final MovieService movieService;

	private final SerieServiceClient serieServiceClient;


	public CatalogController(MovieService movieService, SerieServiceClient serieServiceClient) {
		this.movieService = movieService;
		this.serieServiceClient = serieServiceClient;
	}

	@GetMapping("/movies/{genre}")
	ResponseEntity<List<MovieServiceClient.MovieDto>> getMoviesGenre(@PathVariable String genre) {

		return ResponseEntity.ok(movieService.findByGenre(genre));
	}

	@GetMapping("/series/{genre}")
	ResponseEntity<List<SerieServiceClient.SeriesDto>> getSeriesGenre(@PathVariable String genre) {
		return ResponseEntity.ok(serieServiceClient.getSeriesByGenre(genre));


	}

	@PostMapping
	ResponseEntity<Long> saveMovie(@RequestBody MovieServiceClient.MovieDto movie) {
		Long movieId = movieService.save(movie);
		return ResponseEntity.ok(movieId);
		//return ResponseEntity.ok().body(movieService.save(movie));
	}


}
