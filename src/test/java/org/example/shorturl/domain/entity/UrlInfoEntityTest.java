package org.example.shorturl.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UrlInfoEntityTest {

    @Test
    @DisplayName("최초 UrlCountEntity 생성시 count 값은 0이다.")
    void init() {
        // given - when
        UrlInfoEntity urlInfoEntity = getUrlInfoEntity();

        // then
        assertThat(urlInfoEntity.getCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("ShortUrl 호출시 Count 1 증가한다.")
    void increaseCount() {
        // given
        UrlInfoEntity urlInfoEntity = getUrlInfoEntity();

        // when
        urlInfoEntity.increaseCount();

        // then
        assertThat(urlInfoEntity.getCount()).isEqualTo(1);
    }

    private UrlInfoEntity getUrlInfoEntity() {
        UrlEntity urlEntity = new UrlEntity("https://www.naver.com");
        UrlInfoEntity urlInfoEntity = new UrlInfoEntity(urlEntity);
        return urlInfoEntity;
    }
}