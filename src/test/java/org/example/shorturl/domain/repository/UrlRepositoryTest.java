package org.example.shorturl.domain.repository;

import org.example.shorturl.annotation.JpaTestAnnotation;
import org.example.shorturl.domain.entity.UrlEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UrlRepositoryTest extends JpaTestAnnotation{

    @Autowired
    UrlRepository urlRepository;

    @Test
    @DisplayName("originUrl이 DB에 저장되어 있다면 TRUE를 반환한다.")
    void OriginUrlStored_True() {
        // given
        UrlEntity urlEntity = new UrlEntity("https://www.naver.com");
        UrlEntity savedEntity = urlRepository.save(urlEntity);

        String shortUrl = "http://localhost:8080/redirect/AAAAAAAB";
        savedEntity.setShortUrl(shortUrl);

        // when
        boolean result = urlRepository.existsByOriginUrl(urlEntity.getOriginUrl());

        // then
        assertThat(result).isTrue();
    }


    @Test
    @DisplayName("originUrl이 DB에 저장되어 있지 않다면 FALSE를 반환한다.")
    void OriginUrlNotStored_False() {
        // given - when
        boolean result = urlRepository.existsByOriginUrl("https://www.naver.com");

        // then
        assertThat(result).isFalse();
    }

}