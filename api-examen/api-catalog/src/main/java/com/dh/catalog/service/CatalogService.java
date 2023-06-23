package com.dh.catalog.service;
import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.controller.CatalogDto;
import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.model.serie.Serie;
import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.repository.SerieRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CatalogService {

    private final MovieServiceClient movieServiceClient;
    private final SerieServiceClient serieServiceClient;
    private final MovieRepository movieRepository;
    private final SerieRepository serieRepository;


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
        return movies.stream().map(this::mapToMovie).toList();
    }
    private MovieServiceClient.MovieDto mapToMovie(Movie movie){
        return MovieServiceClient.MovieDto.builder()
                .id(Long.parseLong(movie.getId().replaceAll("[^0-9]", "").substring(0, Math.min(10, movie.getId().length())))) // PARA QUE EL CAMPO DE ID NO DEVUELVA NULL---
                .name(movie.getName())
                .genre(movie.getGenre())
                .urlStream("Retrieved from API-CATALOG.")
                .build();
    }


    @Retry(name = "retrySeriesSearch")
    @CircuitBreaker(name = "seriesFindBy", fallbackMethod = "findSeriesFallBack")
    public List<SerieServiceClient.SeriesDto> getSeriesByGenre(String genre) {
        var series = serieServiceClient.getSeriesByGenre(genre);
        return series;
    }

    public List<SerieServiceClient.SeriesDto> findSeriesFallBack(String genre, Throwable t)  {
        var series = serieRepository.findAllByGenre(genre);
        return series.stream().map(this::mapToSeries).toList();
    }
    private SerieServiceClient.SeriesDto mapToSeries(Serie series){
        return SerieServiceClient.SeriesDto.builder()
                .id(series.getId())
                .name(series.getName())
                .genre(series.getGenre())
                .seasons(series.getSeasons())
                .build();

    }

}
