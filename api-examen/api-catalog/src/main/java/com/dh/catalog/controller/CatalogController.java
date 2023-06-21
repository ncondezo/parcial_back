package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;

import com.dh.catalog.client.SerieServiceClient;

import com.dh.catalog.service.CatalogService;
import com.dh.catalog.service.MovieService;
import com.dh.catalog.service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final MovieService movieService;

	private final SerieService serieService;

	private final CatalogService catalogService;


	public CatalogController(MovieService movieService, SerieService serieService, CatalogService catalogService) {
		this.movieService = movieService;
		this.serieService = serieService;
		this.catalogService = catalogService;
	}

	@GetMapping("/movies/{genre}")
	ResponseEntity<List<MovieServiceClient.MovieDto>> getMoviesGenre(@PathVariable String genre) {

		return ResponseEntity.ok(movieService.findByGenre(genre));
	}

	@GetMapping("/series/{genre}")
	ResponseEntity<List<SerieServiceClient.SeriesDto>> getSeriesGenre(@PathVariable String genre) {
		return ResponseEntity.ok(serieService.getSeriesByGenre(genre));


	}

	@PostMapping("/movies")
	ResponseEntity<Long> saveMovie(@RequestBody MovieServiceClient.MovieDto movie) {
		Long movieId = movieService.save(movie);
		return ResponseEntity.ok(movieId);
		//return ResponseEntity.ok().body(movieService.save(movie));
	}
	@PostMapping("/series")
	ResponseEntity<String> saveSeries(@RequestBody SerieServiceClient.SeriesDto serie){
		String serieId = serieService.save(serie);
		return ResponseEntity.ok(serieId);
	}

	@GetMapping("/{genre}")
	ResponseEntity<CatalogDto> getAllByGenre(@PathVariable String genre){
		return ResponseEntity.ok(catalogService.getAllByGenre(genre));
		//return ResponseEntity.ok( this.generarta(genre));

	}


	public CatalogDto generarta (String genre){
		var movieList = catalogService.findByGenre(genre);
		var seriesList = catalogService.getSeriesByGenre(genre);
		var catalogDto = new CatalogDto();
		catalogDto.setMovies(movieList);
		catalogDto.setSeries(seriesList);

		return catalogDto;
	}


}
