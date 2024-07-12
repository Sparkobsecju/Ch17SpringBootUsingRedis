package org.example.ch17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Ch17Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch17Application.class, args);
    }

}
