package com.kabanov.shortner.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Kabanov Alexey
 */
@Component
@ConfigurationProperties("app-properties")
public class ApplicationProperties {
    
    private int shortUrlLength;

    public void setShortUrlLength(int shortUrlLength) {
        this.shortUrlLength = shortUrlLength;
    }

    public int getShortUrlLength() {
        return shortUrlLength;
    }
}
