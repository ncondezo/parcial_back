package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.mapper.MovieMapper;
import com.dh.catalog.mapper.MovieMapperImpl;
import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.repository.MovieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MovieService {

    private final MovieServiceClient movieServiceClient;
    private final MovieRepository movieRepository;

    private MovieMapper movieMapper = new MovieMapperImpl();

    public MovieService(MovieServiceClient movieServiceClient, MovieRepository movieRepository) {
        this.movieServiceClient = movieServiceClient;

        this.movieRepository = movieRepository;
    }


    @Retry(name = "retryMovieSearch")
    @CircuitBreaker(name = "moviesFindBy", fallbackMethod = "findMoviesFallBack")
    public List<MovieServiceClient.MovieDto> findByGenre(String genre) {
        var movies = movieServiceClient.getMovieByGenre(genre);
        return movies;
    }

    public List<MovieServiceClient.MovieDto> findMoviesFallBack(String genre, Throwable t) throws Exception {
        throw new Exception("No movies found.");
    }

    /*
    @Transactional
    public Long save(MovieServiceClient.MovieDto movie) {

        ResponseEntity<Long> response = movieServiceClient.save(movie);
        if (response.getStatusCode() == HttpStatus.CREATED){
            return response.getBody();
        }else{
            throw new RuntimeException("Error saving movie.");
        }

    }   */

    @Transactional
    public Long save(MovieServiceClient.MovieDto movie) {
        try {
            return executeSave(movie);
        } catch (Exception e) {
            return saveMovieFallback(movie, e);
        }
    }
    @CircuitBreaker(name = "saveMovie", fallbackMethod = "saveMovieFallback")
    private Long executeSave(MovieServiceClient.MovieDto movie) {
        ResponseEntity<Long> response = movieServiceClient.save(movie);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error saving movie.");
        }
    }

    private Long saveMovieFallback(MovieServiceClient.MovieDto movie, Throwable t) {
        // Implementa aquí la lógica de fallback en caso de fallo
        // ...

        return 666L ; // Devuelve un valor de fallback apropiado
    }





}
