package com.dh.apiserie.event;

import com.dh.apiserie.config.RabbitMQConfig;
import com.dh.apiserie.model.Season;
import lombok.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeriesSavedEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public SeriesSavedEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishSeriesSavedEvent(SeriesSavedEventProducer.Data message){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME_2,RabbitMQConfig.TOPIC_SERIES_SAVED,message);
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Data{
        private String name;
        private String genre;
        private List<Season> seasons = new ArrayList<>();
    }
}
