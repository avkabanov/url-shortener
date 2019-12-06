package com.kabanov.shortner.controller.request;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * @author Kabanov Alexey
 */

@Validated
public class ShortenUrlRequest {

    @NotNull(message = "Full url can not be null")
    private String fullUrl;

    public ShortenUrlRequest() {
    }

    public ShortenUrlRequest(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }
}
