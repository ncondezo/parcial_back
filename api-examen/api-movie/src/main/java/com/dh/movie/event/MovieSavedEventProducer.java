package com.dh.movie.event;

import com.dh.movie.config.RabbitMQConfig;
import lombok.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MovieSavedEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public MovieSavedEventProducer(RabbitTemplate rabbitTemplate) {this.rabbitTemplate = rabbitTemplate; }

    public void publishMovieSavedEvent(MovieSavedEventProducer.Data message){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.TOPIC_MOVIE_SAVED,message);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Data{

        private String name;
        private String genre;
    }
}
