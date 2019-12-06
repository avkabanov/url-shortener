package com.kabanov.shortner.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kabanov Alexey
 */
public class ShortUrlGeneratorTest {

    @Test
    public void generateUrlShouldBeSpecifiedLength() {
        final int shortUrlSize = 10;
        ShortUrlGenerator generator = new ShortUrlGenerator(shortUrlSize);

        String result = generator.generate();
        Assert.assertEquals(shortUrlSize, result.length());
        
    }

}