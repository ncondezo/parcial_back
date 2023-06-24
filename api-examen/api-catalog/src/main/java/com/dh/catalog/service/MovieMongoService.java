package com.dh.catalog.service;

import com.dh.catalog.event.MovieSavedEventConsumer;
import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieMongoService {
    @Autowired
    private final MovieRepository movieRepository;

    public MovieMongoService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void save (MovieSavedEventConsumer.Data message){
        movieRepository.save(Movie.builder()
                .name(message.getName())
                .genre(message.getGenre())
                .build());
    }

}
