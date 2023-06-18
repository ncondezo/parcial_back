package com.dh.movie.service;


import com.dh.movie.event.MovieSavedEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {


    private final MovieRepository movieRepository;

    private final MovieSavedEventProducer movieSavedEventProducer;
    public MovieService(MovieRepository movieRepository,MovieSavedEventProducer movieSavedEventProducer) {
        this.movieRepository = movieRepository;
        this.movieSavedEventProducer = movieSavedEventProducer;
    }

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    /*public Movie save(Movie movie) {
        return movieRepository.save(movie);

    }*/
    public Long save(Movie m){

        try {
            var movie = new Movie();
            movie.setGenre(m.getGenre());
            movie.setName(m.getName());
            movie.setUrlStream(m.getUrlStream());
            Movie savedMovie = movieRepository.save(movie);
            movieSavedEventProducer.publishMovieSavedEvent(
                    new MovieSavedEventProducer.Data(
                            savedMovie.getName(),
                            savedMovie.getGenre()
                    )
            );
            return savedMovie.getId();
        } catch(Exception e){
            e.printStackTrace();
            return -1L;
        }
    }
}
