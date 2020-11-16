package com.myretailapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@SpringBootApplication
@EnableMongoRepositories("com.myretailapi.Dao")
@ComponentScan({"com.myretailapi.Controller","com.myretailapi.Model", "com.myretailapi.Handler","com.myretailapi.Service"})
public class ApplicationAPI {
    private final static Logger LOGGER = Logger.getLogger(ApplicationAPI.class.getName());

    /* Main method to launch application */
    public static void main(String[] args) {
        LOGGER.info("Starting up com.myretailapi.ApplicationAPI");
        SpringApplication.run(ApplicationAPI.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
