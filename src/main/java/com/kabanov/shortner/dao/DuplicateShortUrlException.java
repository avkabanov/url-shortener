package com.kabanov.shortner.dao;

/**
 * @author Kabanov Alexey
 */
public class DuplicateShortUrlException extends Exception {

    public DuplicateShortUrlException(String message) {
        super(message);
    }
}
