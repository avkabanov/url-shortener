package com.kabanov.shortner.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.kabanov.shortner.domain.UrlObject;
import com.kabanov.shortner.service.UrlService;

/**
 * @author Kabanov Alexey
 */
@RestController
@RequestMapping(path = "*")
public class ForwardingController {

    private UrlService urlService;

    @Autowired
    public ForwardingController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping(path = "/{shortUrl}")
    public void forwardToFullUrl(@PathVariable String shortUrl, HttpServletResponse httpServletResponse) {
        Optional<UrlObject> optional = urlService.getUrlObjectByShortUrl(shortUrl);
        if (optional.isPresent()) {
            httpServletResponse.setHeader(HttpHeaders.LOCATION, optional.get().getFullUrl());
            httpServletResponse.setStatus(HttpStatus.FOUND.value());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Full url was not found for the shorten: " + shortUrl);
        }
    }

}
