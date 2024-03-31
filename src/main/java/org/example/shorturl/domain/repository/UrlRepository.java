package org.example.shorturl.domain.repository;

import org.example.shorturl.domain.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long>, UrlRepositoryCustom {

    boolean existsByOriginUrl(String originUrl);

    Optional<UrlEntity> findByShortUrl(String shortUrl);
}
