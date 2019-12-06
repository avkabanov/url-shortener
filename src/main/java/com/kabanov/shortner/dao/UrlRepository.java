package com.kabanov.shortner.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabanov.shortner.domain.UrlObject;

/**
 * @author Kabanov Alexey
 */
public interface UrlRepository extends JpaRepository<UrlObject, UUID> {

    Optional<UrlObject> findByShortUrl(String url);
}
