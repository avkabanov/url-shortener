package com.kabanov.shortner.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kabanov.shortner.controller.request.ShortenUrlRequest;
import com.kabanov.shortner.domain.UrlObject;
import com.kabanov.shortner.service.UrlService;

import io.micrometer.core.instrument.Metrics;

/**
 * @author Kabanov Alexey
 */
@Validated
@RestController
@RequestMapping(path = "/url")
public class ShortenerController {
    
    private UrlService urlService;

    @Autowired
    public ShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(path = "/short")
    @ResponseBody
    public ResponseEntity<String> createShortUrl(@Valid @RequestBody ShortenUrlRequest shortenUrlRequest) {
        Metrics.counter("url.request.create.short.url").increment();
        UrlObject result = urlService.createShortUrl(shortenUrlRequest);
        return new ResponseEntity<>(result.getShortUrl(), HttpStatus.CREATED);
    }

}
