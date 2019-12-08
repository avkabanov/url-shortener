package com.kabanov.shortner.service;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabanov.shortner.controller.request.ShortenUrlRequest;
import com.kabanov.shortner.dao.DuplicateShortUrlException;
import com.kabanov.shortner.dao.UrlCache;
import com.kabanov.shortner.domain.UrlObject;

import io.micrometer.core.instrument.Metrics;

/**
 * @author Kabanov Alexey
 */
@Service
public class UrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    private ShortUrlGenerator shortUrlGenerator;
    private UrlCache urlCache;

    @Autowired
    public UrlService(ShortUrlGenerator shortUrlGenerator, UrlCache urlCache) {
        this.shortUrlGenerator = shortUrlGenerator;
        this.urlCache = urlCache;
    }

    @Nonnull
    public UrlObject createShortUrl(@Nonnull ShortenUrlRequest shortenUrlRequest) {
        String fullUrl = shortenUrlRequest.getFullUrl();
        String shortUrl;

        UrlObject savedObject = null;
        do {
            shortUrl = shortUrlGenerator.generate();
            UrlObject urlObject = createUrlObject(fullUrl, shortUrl);
            try {
                savedObject = urlCache.save(urlObject);
                logger.warn("Short url to full url mapping created: " + savedObject);
            } catch (DuplicateShortUrlException e) {
                logger.warn("Duplicate short url: " + urlObject.getShortUrl() + " Regenerating short url...");
                // ignore. Will regenerate url
            }
        } while (savedObject == null);
        Metrics.gauge("unique.values.used.percentage", getUniqueValuesUsedPercentage());

        return savedObject;
    }

    private int getUniqueValuesUsedPercentage() {
        return (int) (urlCache.getNumberOfShortUrls() / shortUrlGenerator.getMaxNumberPossibleUniqueValues()) * 100;
    }

    private UrlObject createUrlObject(String fullUrl, String shortUrl) {
        return new UrlObject(fullUrl, shortUrl);
    }

    public Optional<UrlObject> getUrlObjectByShortUrl(String shortUrl) {
        return urlCache.getUrlObjectByShortUrl(shortUrl);
    }
}
