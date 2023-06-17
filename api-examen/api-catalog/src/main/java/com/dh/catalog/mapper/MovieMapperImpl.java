package com.dh.catalog.mapper;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.model.movie.Movie;

public class MovieMapperImpl implements MovieMapper{

    @Override
    public Movie mapToMovie(MovieServiceClient.MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setId(movieDto.getId().toString());
        movie.setName(movieDto.getName());
        movie.setGenre(movieDto.getGenre());
        return movie;
    }
}
