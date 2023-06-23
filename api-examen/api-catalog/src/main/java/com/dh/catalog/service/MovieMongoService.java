package com.dh.catalog.service;

import com.dh.catalog.event.MovieSavedEventConsumer;
import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieMongoService {

    private final MovieRepository movieRepository;

    public MovieMongoService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /*public void save(MovieSavedEventConsumer.Data message){
        var movie = new Movie();
        movie.setName(message.getName());
        movie.setGenre(message.getGenre());
        movieRepository.save(movie);
    }*/
    public void save(MovieSavedEventConsumer.Data message){
        var movie = Movie.builder()
                .name(message.getName())
                .genre(message.getGenre())
                .build();
        movieRepository.save(movie);
    }
}
