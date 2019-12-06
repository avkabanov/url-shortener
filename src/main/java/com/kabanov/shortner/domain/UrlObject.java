package com.kabanov.shortner.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Kabanov Alexey
 */
@Entity
public class UrlObject {
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private UUID uuid;
    
    @Column(name = "fullUrl")
    private String fullUrl;

    @Column(unique = true, name = "shortUrl")
    private String shortUrl;
    
    // TODO do i need it?
    @Column(name = "shortHash")
    private int shortUrlHash;

    public UrlObject() {
    }

    public UrlObject(String fullUrl, String shortUrl, int shortUrlHash) {
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.shortUrlHash = shortUrlHash;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getShortUrlHash() {
        return shortUrlHash;
    }

    public void setShortUrlHash(int shortUrlHash) {
        this.shortUrlHash = shortUrlHash;
    }
}
