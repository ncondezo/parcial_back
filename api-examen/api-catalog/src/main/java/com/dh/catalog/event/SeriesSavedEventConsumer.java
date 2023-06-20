package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.serie.Season;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.ArrayList;
import java.util.List;

public class SeriesSavedEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SERIES_SAVED)
    public void listen(SeriesSavedEventConsumer.Data message){
        System.out.println("Series info: " + message.toString());

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
