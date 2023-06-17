package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SerieService {

    private final SerieServiceClient serieServiceClient;


    public SerieService(SerieServiceClient serieServiceClient) {
        this.serieServiceClient = serieServiceClient;
    }

    @Retry(name = "retrySeriesSearch")
    @CircuitBreaker(name = "seriesFindBy", fallbackMethod = "findSeriesFallBack")
    public List<SerieServiceClient.SeriesDto> getSeriesByGenre(String genre) {
        var series = serieServiceClient.getSeriesByGenre(genre);
        return series;
    }
    public List<SerieServiceClient.SeriesDto> findSeriesFallBack(String genre, Throwable t)  {
        var serie = new SerieServiceClient.SeriesDto();
        serie.setId("API-SERIES UNAVAILABLE");
        serie.setName("API-SERIES UNAVAILABLE");
        serie.setGenre("---------------------");
        serie.setSeasons(new ArrayList<>());
        var lista = new ArrayList<SerieServiceClient.SeriesDto>();
        lista.add(serie);

        return lista;
    }

    public String save(SerieServiceClient.SeriesDto series) {
        ResponseEntity<String> response = serieServiceClient.save(series);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error saving series.");
        }
    }

}
