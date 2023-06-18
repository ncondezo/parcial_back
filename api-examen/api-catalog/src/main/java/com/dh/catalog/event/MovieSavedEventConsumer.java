package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MovieSavedEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_MOVIE_SAVED)
    public void listen(MovieSavedEventConsumer.Data message){
        System.out.println("Movie info: " + message);


    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private String name;
        private String genre;
    }
}
