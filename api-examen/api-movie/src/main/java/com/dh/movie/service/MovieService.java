package com.dh.movie.service;


import com.dh.movie.event.MovieSavedEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieSavedEventProducer movieSavedEventProducer;

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Long save (Movie m){
        var savedMovie = movieRepository.save(Movie.builder()
                .name(m.getName())
                .genre(m.getGenre())
                .urlStream(m.getUrlStream())
                .build());
        movieSavedEventProducer.publishMovieSavedEvent(
                MovieSavedEventProducer.Data.builder()
                        .name(savedMovie.getName())
                        .genre(savedMovie.getGenre())
                        .build()
        );
        return savedMovie.getId();
    }

}
