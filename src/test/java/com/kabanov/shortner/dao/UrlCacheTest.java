package com.kabanov.shortner.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kabanov.shortner.domain.UrlObject;

/**
 * @author Kabanov Alexey
 */
@RunWith(SpringRunner.class)
@ImportResource("classpath:spring-config.xml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UrlCacheTest {

    @Autowired private UrlCache urlCache;

    @Test(expected = DuplicateShortUrlException.class)
    public void shouldThrowExceptionOnAddingExistingShortUrl() throws DuplicateShortUrlException {
        final String shortUrl = "randomUrl";
        
        urlCache.save(new UrlObject("google.com", shortUrl, 1));
        urlCache.save(new UrlObject("yahoo.com.uk", shortUrl, 1));
    }

}