package com.dh.catalog.service;

import com.dh.catalog.event.SeriesSavedEventConsumer;
import com.dh.catalog.model.serie.Serie;
import com.dh.catalog.repository.SerieRepository;
import org.springframework.stereotype.Service;

@Service
public class SeriesMongoService {

    private final SerieRepository serieRepository;

    public SeriesMongoService(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void save (SeriesSavedEventConsumer.Data message){
        serieRepository.save(Serie.builder()
                .name(message.getName())
                .genre(message.getGenre())
                .seasons(message.getSeasons())
                .build());
    }
}
