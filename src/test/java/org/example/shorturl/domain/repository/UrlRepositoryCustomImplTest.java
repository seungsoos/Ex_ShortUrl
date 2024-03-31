package org.example.shorturl.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.example.shorturl.domain.dto.response.GetAllUrlsResponse;
import org.example.shorturl.domain.entity.UrlCountEntity;
import org.example.shorturl.domain.entity.UrlEntity;
import org.example.shorturl.util.Base62Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UrlRepositoryCustomImplTest {

    @Autowired
    JPAQueryFactory queryFactory;
    @Autowired
    UrlRepository urlRepository;
    @Autowired
    UrlCountRepository urlCountRepository;
    @Autowired
    Base62Util base62Util;
    private final String REDIRECT_URL = "http://localhost:8080/redirect/";

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void clean(){
        this.entityManager.createNativeQuery("ALTER TABLE TB_URL AUTO_INCREMENT = 1").executeUpdate();

    }
    @Test
    @DisplayName("URL 전체 조회")
    void UrlRepositoryCustomImplTest() {
        // given
        UrlEntity urlEntity1 = new UrlEntity("https://www.naver.com");
        UrlEntity urlEntity2 = new UrlEntity("https://www.kakao.com");
        UrlEntity urlEntity3 = new UrlEntity("https://www.line.com");
        UrlEntity urlEntity4 = new UrlEntity("https://www.coupang.com");
        UrlEntity urlEntity5 = new UrlEntity("https://www.baemin.com");

        List<UrlEntity> urlEntities = urlRepository.saveAll(List.of(urlEntity1, urlEntity2, urlEntity3, urlEntity4, urlEntity5));
        urlEntities.forEach(
                        urlEntity -> {
                            String encode = base62Util.encode(urlEntity.getId());
                            urlEntity.setShortUrl(REDIRECT_URL+encode);
                        }
                );

        UrlCountEntity urlCountEntity1 = new UrlCountEntity(urlEntity1);
        urlCountEntity1.increaseCount();
        UrlCountEntity urlCountEntity2 = new UrlCountEntity(urlEntity2);
        urlCountEntity2.increaseCount();
        UrlCountEntity urlCountEntity3 = new UrlCountEntity(urlEntity3);
        urlCountEntity3.increaseCount();
        UrlCountEntity urlCountEntity4 = new UrlCountEntity(urlEntity4);
        UrlCountEntity urlCountEntity5 = new UrlCountEntity(urlEntity5);

        urlCountRepository.saveAll(List.of(urlCountEntity1, urlCountEntity2, urlCountEntity3, urlCountEntity4, urlCountEntity5));

        Pageable pageable = PageRequest.of(0,20);
        // when
        Page<GetAllUrlsResponse> getAllUrlsResponses = urlRepository.findAllByUrl(pageable);

        // then
        List<GetAllUrlsResponse> content = getAllUrlsResponses.getContent();
        assertThat(content)
                .extracting("originUrl","shortUrl","count")
                .containsExactlyInAnyOrder(
                        tuple("https://www.naver.com","http://localhost:8080/redirect/AAAAAAAB", 1L),
                        tuple("https://www.kakao.com","http://localhost:8080/redirect/AAAAAAAC", 1L),
                        tuple("https://www.line.com","http://localhost:8080/redirect/AAAAAAAD", 1L),
                        tuple("https://www.coupang.com","http://localhost:8080/redirect/AAAAAAAE", 0L),
                        tuple("https://www.baemin.com","http://localhost:8080/redirect/AAAAAAAF", 0L)
                );

    }
}