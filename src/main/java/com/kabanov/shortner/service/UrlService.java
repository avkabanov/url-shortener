package com.kabanov.shortner.service;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabanov.shortner.controller.request.ShortenUrlRequest;
import com.kabanov.shortner.dao.DuplicateShortUrlException;
import com.kabanov.shortner.dao.UrlCache;
import com.kabanov.shortner.domain.UrlObject;

/**
 * @author Kabanov Alexey
 */
@Service
public class UrlService {

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
        do {
            shortUrl = shortUrlGenerator.generate();
            UrlObject urlObject = createUrlObject(fullUrl, shortUrl);
            try {
                return urlCache.save(urlObject);
            } catch (DuplicateShortUrlException e) {
                // sout expection
            }
        } while (true);
    }

    private UrlObject createUrlObject(String fullUrl, String shortUrl) {
        return new UrlObject(fullUrl, shortUrl, shortUrl.hashCode());
    }

    public Optional<UrlObject> getUrlObjectByShortUrl(String shortUrl) {
        return urlCache.getUrlObjectByShortUrl(shortUrl);
    }
}
