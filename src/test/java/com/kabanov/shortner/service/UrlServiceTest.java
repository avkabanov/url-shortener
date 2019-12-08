package com.kabanov.shortner.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.kabanov.shortner.controller.request.ShortenUrlRequest;
import com.kabanov.shortner.dao.DuplicateShortUrlException;
import com.kabanov.shortner.dao.UrlCache;
import com.kabanov.shortner.domain.UrlObject;

/**
 * @author Kabanov Alexey
 */
@RunWith(SpringRunner.class)
@ImportResource("classpath:spring-config.xml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UrlServiceTest {
    
    @Autowired private UrlService urlService;
    @MockBean private UrlCache urlCache;
    @SpyBean private ShortUrlGenerator shortUrlGenerator;
    
    @Test
    public void shouldRegenerateUrlOnDuplicateShortUId() throws DuplicateShortUrlException {
        Mockito.when(urlCache.save(Mockito.any()))
                .thenThrow(new DuplicateShortUrlException("duplicate"))
                .thenReturn(new UrlObject("google.com", "someShortUrl"));
        

        ShortenUrlRequest shortenUrlRequest = new ShortenUrlRequest("google.com");
        urlService.createShortUrl(shortenUrlRequest);
        
        Mockito.verify(shortUrlGenerator, Mockito.times(2)).generate();
    }
}