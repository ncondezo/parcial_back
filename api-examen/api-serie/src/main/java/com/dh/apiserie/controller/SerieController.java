package com.dh.apiserie.controller;

import com.dh.apiserie.model.Serie;
import com.dh.apiserie.service.SerieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @GetMapping("/{genre}")
    ResponseEntity<List<Serie>> getSerieByGenre(@PathVariable String genre){
        return ResponseEntity.ok(serieService.getSeriesBygGenre(genre));
    }

    @PostMapping("/save")
    public ResponseEntity<String>saveSerie(@RequestBody Serie serie){
        String movieId = serieService.save(serie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieId);
    }

}
