package com.kabanov.shortner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.kabanov.shortner.properties.ApplicationProperties;

/**
 * @author Kabanov Alexey
 */
@Component
public class ShortUrlGeneratorFactory {
    private ApplicationProperties applicationProperties;

    @Autowired
    public ShortUrlGeneratorFactory(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    ShortUrlGenerator createShortUrlGenerator() {
        return new ShortUrlGenerator(applicationProperties.getShortUrlLength());
    }
}
