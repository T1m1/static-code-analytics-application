package de.htwg.konstanz.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ValidatorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidatorServiceApplication.class, args);
    }
}