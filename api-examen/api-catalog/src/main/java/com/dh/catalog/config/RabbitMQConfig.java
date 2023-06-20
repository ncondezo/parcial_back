package com.dh.catalog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "movieExchange";
    public static final String TOPIC_MOVIE_SAVED = "com.dh.movie.movieSaved";
    public static final String QUEUE_MOVIE_SAVED = "queueMovieSaved";


    public static final String EXCHANGE_NAME_2 = "seriesExchange";

    public static final String TOPIC_SERIES_SAVED = "com.dh.series.seriesSaved";

    public static final String QUEUE_SERIES_SAVED = "queueSeriesSaved";


    @Bean
    public TopicExchange appExchange(){return new TopicExchange(EXCHANGE_NAME);}
    @Bean
    public Queue queueMovieSaved(){return new Queue(QUEUE_MOVIE_SAVED);}
    @Bean
    public Binding createBinding(){
        return BindingBuilder.bind(queueMovieSaved()).to(appExchange()).with(TOPIC_MOVIE_SAVED);
    }


    @Bean
    public TopicExchange seriesExchange(){return new TopicExchange(EXCHANGE_NAME_2);}
    @Bean
    public Queue queueSeriesSaved(){return new Queue(QUEUE_SERIES_SAVED);}
    @Bean
    public Binding seriesBinding(){
        return BindingBuilder.bind(queueSeriesSaved()).to(seriesExchange()).with(TOPIC_SERIES_SAVED);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(consumerJackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter consumerJackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


}
