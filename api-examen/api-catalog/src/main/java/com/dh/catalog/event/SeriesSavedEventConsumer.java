package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.serie.Season;
import com.dh.catalog.service.SeriesMongoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class SeriesSavedEventConsumer {

    private final SeriesMongoService seriesMongoService;
    public SeriesSavedEventConsumer(SeriesMongoService seriesMongoService){
        this.seriesMongoService=seriesMongoService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SERIES_SAVED)
    public void listen(SeriesSavedEventConsumer.Data message){
        seriesMongoService.save(message);



    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private String name;
        private String genre;
        private List<Season> seasons = new ArrayList<>();
    }
}
