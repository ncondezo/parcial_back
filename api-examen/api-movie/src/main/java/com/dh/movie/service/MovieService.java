package com.dh.movie.service;


import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {


    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    /*public Movie save(Movie movie) {
        return movieRepository.save(movie);

    }*/
    public Long save(Movie m){
        var movie = new Movie();
        movie.setGenre(m.getGenre());
        movie.setName(m.getName());
        movie.setUrlStream(m.getUrlStream());
        Movie savedMovie = movieRepository.save(movie);
        return savedMovie.getId();
    }
}
