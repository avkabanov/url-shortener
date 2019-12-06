package com.kabanov.shortner.service;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Kabanov Alexey
 */
public class ShortUrlGenerator {
    
    private final int shortUrlSize;

    public ShortUrlGenerator(int shortUrlSize) {
        this.shortUrlSize = shortUrlSize;
    }

    public String generate() {
        return RandomStringUtils.randomAlphanumeric(shortUrlSize);
    }
}
