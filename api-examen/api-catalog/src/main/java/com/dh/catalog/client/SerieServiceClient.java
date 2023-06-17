package com.dh.catalog.client;

import com.dh.catalog.model.serie.Season;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="api-serie")
public interface SerieServiceClient {

    @GetMapping("/api/v1/series/{genre}")
    List<SerieServiceClient.SeriesDto> getSeriesByGenre(@PathVariable(value = "genre") String genre);


    @Getter
    @Setter
    class SeriesDto{
        private String id;
        private String name;
        private String genre;
        private List<Season> seasons = new ArrayList<>();
    }


}
