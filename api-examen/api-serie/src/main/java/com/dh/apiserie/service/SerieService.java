package com.dh.apiserie.service;

import com.dh.apiserie.event.SeriesSavedEventProducer;
import com.dh.apiserie.model.Serie;
import com.dh.apiserie.repository.SerieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SerieService {

    private final SerieRepository repository;
    private final SeriesSavedEventProducer seriesSavedEventProducer;

    public List<Serie> getSeriesBygGenre(String genre) {
        return repository.findAllByGenre(genre);
    }

    public String save(Serie s) {
        var savedSerie =repository.save(Serie.builder()
                .name(s.getName())
                .genre(s.getGenre())
                .seasons(s.getSeasons())
                .build());
        seriesSavedEventProducer.publishSeriesSavedEvent(
                SeriesSavedEventProducer.Data.builder()
                        .name(savedSerie.getName())
                        .genre(savedSerie.getGenre())
                        .seasons(savedSerie.getSeasons())
                        .build());
        return savedSerie.getId();

    }

}
