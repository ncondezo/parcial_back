package com.dh.catalog.mapper;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.model.movie.Movie;

public interface MovieMapper {

    Movie mapToMovie(MovieServiceClient.MovieDto movieDto);
}
