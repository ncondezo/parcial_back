package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.model.movie.Movie;
import com.dh.catalog.service.MovieMongoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MovieSavedEventConsumer {

    private final MovieMongoService movieMongoService;

    public MovieSavedEventConsumer(MovieMongoService movieMongoService) {
        this.movieMongoService = movieMongoService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_MOVIE_SAVED)
    public void listen(MovieSavedEventConsumer.Data message){
        movieMongoService.save(message);

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
