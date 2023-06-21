package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.model.serie.Serie;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CatalogDto {

    private List<MovieServiceClient.MovieDto> movies;
    private List<SerieServiceClient.SeriesDto> series;
}
