package com.dh.catalog.mapper;

import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.serie.Serie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class SeriesMapper {

    public SerieServiceClient.SeriesDto mapToDto(Serie serie){
        var serieDto = new SerieServiceClient.SeriesDto();
        serieDto.setId(serie.getId());
        serieDto.setName(serie.getName());
        serieDto.setGenre(serie.getGenre());
        serieDto.setSeasons(serie.getSeasons());
        return serieDto;
    }
}
