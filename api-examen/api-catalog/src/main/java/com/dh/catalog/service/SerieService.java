package com.dh.catalog.service;

import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.mapper.SeriesMapper;
import com.dh.catalog.model.serie.Serie;
import com.dh.catalog.repository.SerieRepository;
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

    private final SerieRepository serieRepository;

    private final SeriesMapper seriesMapper;


    public SerieService(SerieServiceClient serieServiceClient, SerieRepository serieRepository, SeriesMapper seriesMapper) {
        this.serieServiceClient = serieServiceClient;

        this.serieRepository = serieRepository;
        this.seriesMapper = seriesMapper;
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

    public String save(SerieServiceClient.SeriesDto series) {
        ResponseEntity<String> response = serieServiceClient.save(series);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error saving series.");
        }
    }

}
