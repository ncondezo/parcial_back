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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

    public List<MovieServiceClient.MovieDto> findMoviesFallBack(String genre, Throwable t)  {
        var movie_error = new MovieServiceClient.MovieDto();
        movie_error.setId(999L);
        movie_error.setName("El rey leoón");
        movie_error.setGenre("Animada");
        movie_error.setUrlStream("API-MOVIE UNAVAILABLE");
        var lista_hardcodeada = new ArrayList<MovieServiceClient.MovieDto>();
        lista_hardcodeada.add(movie_error);

        return lista_hardcodeada;
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
    @Retry(name = "retryMovieSave")
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
        // Implementar  lógica de fallback
        // ...

        return 7777777777L ; // Devuelve un valor de fallback apropiado
    }





}
