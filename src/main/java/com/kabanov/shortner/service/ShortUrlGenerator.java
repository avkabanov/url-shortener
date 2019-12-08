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
    
    public long getMaxNumberPossibleUniqueValues() {
        // 26 lowercase letter, 26 uppercase letter, 10 digits  
        return (long)Math.pow(26 + 26 + 10, shortUrlSize); 
    }
}
