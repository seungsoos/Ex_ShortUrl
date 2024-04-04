package org.example.shorturl.service;

import jakarta.persistence.EntityManager;
import org.example.shorturl.common.exception.RootException;
import org.example.shorturl.domain.dto.request.CreateShortUrlRequest;
import org.example.shorturl.domain.dto.response.GetDetailUrlResponse;
import org.example.shorturl.domain.entity.UrlCallHistoryEntity;
import org.example.shorturl.domain.entity.UrlCountEntity;
import org.example.shorturl.domain.entity.UrlEntity;
import org.example.shorturl.domain.repository.UrlCallHistoryRepository;
import org.example.shorturl.domain.repository.UrlCountRepository;
import org.example.shorturl.domain.repository.UrlRepository;
import org.example.shorturl.facade.RedissonLockFacade;
import org.example.shorturl.util.Base62Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
//@Transactional
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
    RedissonLockFacade redissonLockFacade;

    @AfterEach
    public void afterEach() {
        urlCallHistoryRepository.deleteAllInBatch();
        urlCountRepository.deleteAllInBatch();
        urlRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("URL 상세조회")
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
    @DisplayName("originUrl을 입력시 shortUrl을 반환한다.")
    void createShortUrl() {
        // given
        CreateShortUrlRequest createShortUrlRequest = CreateShortUrlRequest.builder()
                .originUrl("https://www.naver.com")
                .build();
        String shortUrl = urlService.createShortUrl(createShortUrlRequest);

        // when
        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RootException("존재하지않는 URL 정보입니다."));

        // then
        assertThat(shortUrl).isEqualTo(urlEntity.getShortUrl());
    }

    @Test
    @DisplayName("ShortUrl 호출시 OriginUrl로 반환을 반환하고, UrlCount를 1증가시킨다.")
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

    @Test
    @DisplayName("동시성 이슈")
    void redirectShortUrl() throws InterruptedException {
        // given
        UrlEntity urlEntity = new UrlEntity("https://www.naver.com");
        String shortUrl = "AAAAAAAB";
        urlEntity.setShortUrl(REDIRECT_URL + shortUrl);
        UrlEntity savedUrlEntity = urlRepository.save(urlEntity);

        UrlCountEntity urlCountEntity = new UrlCountEntity(savedUrlEntity);
        urlCountRepository.save(urlCountEntity);

        int numThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch countDownLatch = new CountDownLatch(numThreads);

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                try {
                    String s = redissonLockFacade.redirectShortUrl(shortUrl);
                    System.out.println("s = " + s);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // when
        UrlEntity dbUrlEntity = urlRepository.findById(urlEntity.getId())
                .orElseThrow();

        // then
        assertThat(dbUrlEntity.getUrlCountEntity().getCount()).isEqualTo(100L);
    }

}