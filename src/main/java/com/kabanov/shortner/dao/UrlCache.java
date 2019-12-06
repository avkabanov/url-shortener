package com.kabanov.shortner.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.kabanov.shortner.domain.UrlObject;

/**
 * @author Kabanov Alexey
 */
@Repository
public class UrlCache {

    private UrlRepository urlRepository;

    @Autowired
    public UrlCache(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlObject save(UrlObject urlObject) throws DuplicateShortUrlException {
        try {
            return urlRepository.save(urlObject);
        } catch (DataIntegrityViolationException e) {
            if (isShortUrlException(e)) {
                throw new DuplicateShortUrlException(urlObject.getShortUrl());
            } else {
                throw e;
            }

        }
    }

    /**
     * I don't ths this method implemented perfectly because of comparing constraint with string field name,
     * but I don't see how to do it in a better way
     */
    private boolean isShortUrlException(DataIntegrityViolationException e) {
        return ((e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
                && ((org.hibernate.exception.ConstraintViolationException)e.getCause()).
                getConstraintName().contains("SHORTURL"));
    }

    public Optional<UrlObject> getUrlObjectByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }
}
