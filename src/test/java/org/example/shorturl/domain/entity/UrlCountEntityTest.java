package org.example.shorturl.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UrlCountEntityTest {

    @Test
    @DisplayName("최초 UrlCountEntity 생성시 count 값은 0이다.")
    void init() {
        // given - when
        UrlCountEntity urlCountEntity = getUrlCountEntity();

        // then
        assertThat(urlCountEntity.getCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("ShortUrl 호출시 Count 1 증가한다.")
    void increaseCount() {
        // given
        UrlCountEntity urlCountEntity = getUrlCountEntity();

        // when
        urlCountEntity.increaseCount();

        // then
        assertThat(urlCountEntity.getCount()).isEqualTo(1);
    }

    private UrlCountEntity getUrlCountEntity() {
        UrlEntity urlEntity = new UrlEntity("https://www.naver.com");
        UrlCountEntity urlCountEntity = new UrlCountEntity(urlEntity);
        return urlCountEntity;
    }
}