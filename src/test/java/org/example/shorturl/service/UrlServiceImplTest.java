package org.example.shorturl.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.shorturl.domain.dto.response.GetDetailUrlResponse;
import org.example.shorturl.domain.entity.UrlCallHistoryEntity;
import org.example.shorturl.domain.entity.UrlCountEntity;
import org.example.shorturl.domain.entity.UrlEntity;
import org.example.shorturl.domain.repository.UrlCallHistoryRepository;
import org.example.shorturl.domain.repository.UrlCountRepository;
import org.example.shorturl.domain.repository.UrlRepository;
import org.example.shorturl.util.Base62Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UrlServiceImplTest {

    @Autowired
    UrlServiceImpl urlService;
    @Autowired
    UrlRepository urlRepository;
    @Autowired
    UrlCountRepository urlCountRepository;
    @Autowired
    UrlCallHistoryRepository urlCallHistoryRepository;
    @Autowired
    Base62Util base62Util;
    String REDIRECT_URL = "http://localhost:8080/redirect/";

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("URL 상세조회")
    @Transactional
    void getDetailUrl() {
        // given
        UrlEntity urlEntity = new UrlEntity("https://www.naver.com");
        UrlEntity savedEntity = urlRepository.save(urlEntity);
        savedEntity.setShortUrl(REDIRECT_URL+ "AAAAAAAB");

        UrlCallHistoryEntity urlCallHistoryEntity1 = new UrlCallHistoryEntity(urlEntity);
        UrlCallHistoryEntity urlCallHistoryEntity2 = new UrlCallHistoryEntity(urlEntity);
        UrlCallHistoryEntity urlCallHistoryEntity3 = new UrlCallHistoryEntity(urlEntity);
        urlCallHistoryRepository.saveAll(List.of(urlCallHistoryEntity1,urlCallHistoryEntity2,urlCallHistoryEntity3));

        // when
        List<UrlCallHistoryEntity> urlCallHistoryEntities = urlCallHistoryRepository.findByUrlEntity(savedEntity);
        List<GetDetailUrlResponse> getDetailUrlResponses = urlCallHistoryEntities.stream()
                .map(UrlCallHistoryEntity::toGetDetailUrlResponse)
                .collect(Collectors.toList());

        // then
        assertThat(getDetailUrlResponses).size().isEqualTo(3);
        assertThat(getDetailUrlResponses)
                .extracting("shortUrl")
                .containsOnly("http://localhost:8080/redirect/AAAAAAAB");
    }

    @Test
    @DisplayName("ShortUrl 호출시 OriginUrl로 반환을 반환하고, UrlCount를 1증가시킨다.")
    @Transactional
    void redirectShortUrlToOriginUrl() {
        // given
        UrlEntity urlEntity = new UrlEntity("https://www.naver.com");
        UrlEntity savedUrlEntity = urlRepository.save(urlEntity);

        UrlCountEntity urlCountEntity = new UrlCountEntity(savedUrlEntity);
        urlCountRepository.save(urlCountEntity);

        String shortUrl = "AAAAAAAB";
        urlEntity.setShortUrl(REDIRECT_URL + shortUrl);

        // when
        String originUrl = urlService.redirectShortUrl(shortUrl);

        // then
        assertThat(savedUrlEntity.getOriginUrl()).isEqualTo(originUrl);
        assertThat(urlCountEntity.getCount()).isEqualTo(1);
    }

}