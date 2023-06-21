package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.controller.CatalogDto;
import com.dh.catalog.mapper.MovieMapper;
import com.dh.catalog.mapper.SeriesMapper;
import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.model.serie.Serie;
import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.repository.SerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CatalogService {

    private final MovieServiceClient movieServiceClient;
    private final SerieServiceClient serieServiceClient;
    private final MovieRepository movieRepository;
    private final SerieRepository serieRepository;
    private final MovieMapper movieMapper;
    private final SeriesMapper seriesMapper;

    public CatalogService(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient, MovieRepository movieRepository, SerieRepository serieRepository, MovieMapper movieMapper, SeriesMapper seriesMapper) {
        this.movieServiceClient = movieServiceClient;
        this.serieServiceClient = serieServiceClient;
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
        this.movieMapper = movieMapper;
        this.seriesMapper = seriesMapper;
    }
    @Lazy
    @Autowired
    private CatalogService self;

    public CatalogDto getAllByGenre (String genre){
        var movieList = self.findByGenre(genre);
        var seriesList = self.getSeriesByGenre(genre);
        var catalogDto = new CatalogDto();
        catalogDto.setMovies(movieList);
        catalogDto.setSeries(seriesList);

        return catalogDto;
    }

    @Retry(name = "retryMovieSearch")
    @CircuitBreaker(name = "moviesFindBy", fallbackMethod = "findMoviesFallBack")
    public List<MovieServiceClient.MovieDto> findByGenre(String genre) {
        var movies = movieServiceClient.getMovieByGenre(genre);
        return movies;
    }

    public List<MovieServiceClient.MovieDto> findMoviesFallBack(String genre, Throwable t) {
        var movies = movieRepository.findAllByGenre(genre);
        var movieDtos = new ArrayList<MovieServiceClient.MovieDto>();
        for (Movie m : movies) {
            MovieServiceClient.MovieDto movieDto = movieMapper.mapToDto(m);
            movieDtos.add(movieDto);
        }

        return movieDtos;
    }
    @Retry(name = "retrySeriesSearch")
    @CircuitBreaker(name = "seriesFindBy", fallbackMethod = "findSeriesFallBack")
    public List<SerieServiceClient.SeriesDto> getSeriesByGenre(String genre) {
        var series = serieServiceClient.getSeriesByGenre(genre);
        return series;
    }
    public List<SerieServiceClient.SeriesDto> findSeriesFallBack(String genre, Throwable t)  {
        var series = serieRepository.findAllByGenre(genre);
        var seriesDtos = new ArrayList<SerieServiceClient.SeriesDto>();
        for (Serie s : series){
            SerieServiceClient.SeriesDto serieDto = seriesMapper.mapToDto(s);
            seriesDtos.add(serieDto);
        }

        return seriesDtos;
    }
}
