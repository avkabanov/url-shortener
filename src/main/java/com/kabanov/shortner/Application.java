package com.kabanov.shortner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Kabanov Alexey
 */

@SpringBootApplication
@ImportResource("classpath:spring-config.xml")
public class Application implements WebMvcConfigurer {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}