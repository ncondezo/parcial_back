package com.dh.catalog.mapper;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.model.movie.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
        public MovieServiceClient.MovieDto mapToDto(Movie movie) {
            MovieServiceClient.MovieDto movieDto = new MovieServiceClient.MovieDto();
            movieDto.setName(movie.getName());
            movieDto.setGenre(movie.getGenre());
            movieDto.setUrlStream("API-MOVIE");


            return movieDto;
        }
}
