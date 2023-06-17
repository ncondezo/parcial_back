package com.dh.apiserie.service;

import com.dh.apiserie.model.Serie;
import com.dh.apiserie.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository repository;



    public SerieService(SerieRepository repository) {
        this.repository = repository;
    }



    public List<Serie> getSeriesBygGenre(String genre) {
        return repository.findAllByGenre(genre);
    }

    //public Serie createSerie(Serie serieDto) {return repository.save(serieDto);}

    public String save(Serie s){
        var serie = new Serie();
        serie.setGenre(s.getGenre());
        serie.setName(s.getName());
        serie.setSeasons(s.getSeasons());
        Serie savedSerie = repository.save(serie);
        return savedSerie.getId();
    }
}
