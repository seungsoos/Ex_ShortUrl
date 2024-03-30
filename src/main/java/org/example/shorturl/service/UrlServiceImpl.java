package org.example.shorturl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.common.exception.RootException;
import org.example.shorturl.util.Base62Util;
import org.example.shorturl.domain.dto.request.CreateShortUrlRequest;
import org.example.shorturl.domain.dto.response.GetAllUrlsResponse;
import org.example.shorturl.domain.entity.UrlEntity;
import org.example.shorturl.domain.enums.UrlType;
import org.example.shorturl.domain.repository.UrlCallHistoryRepository;
import org.example.shorturl.domain.repository.UrlCountRepository;
import org.example.shorturl.domain.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlCountRepository urlCountRepository;
    private final UrlCallHistoryRepository urlCallHistoryRepository;
    private final Base62Util base62Util;
    private final String REDIRECT_URL = "http://localhost:8080/redirect/";


    @Override
    public List<GetAllUrlsResponse> getAllUrls(UrlType urlType) {
        List<UrlEntity> urlEntityList = urlRepository.findAll();

        return null;
    }

    @Override
    public void getDetailUrl(Long id) {

    }


    @Override
    @Transactional
    public String createShortUrl(CreateShortUrlRequest createShortUrlRequest) {
        String originUrl = createShortUrlRequest.getOriginUrl();
        boolean result = urlRepository.existsByOriginUrl(originUrl);

        if (result) {
            throw new RootException("이미 존재하는 URL 입니다.");
        }

        UrlEntity urlEntity = new UrlEntity(originUrl);
        UrlEntity saveUrlEntity = urlRepository.save(urlEntity);

        String shortUrl = REDIRECT_URL + base62Util.encode(saveUrlEntity.getId());
        saveUrlEntity.setShortUrl(shortUrl);
        log.info("saveUrlEntity.getId = {}, shortUrl = {}", saveUrlEntity.getId(), shortUrl);
        return shortUrl;
    }

    @Override
    public String redirectShortUrl(String shortUrl) {
        String redirectUrl = REDIRECT_URL + shortUrl;
        System.out.println("redirectUrl = " + redirectUrl);
        UrlEntity urlEntity = urlRepository.findByShortUrl(redirectUrl)
                .orElseThrow(() -> new RootException("존재하지않는 URL 정보입니다."));


        System.out.println("urlEntity = " + urlEntity);
        return urlEntity.getOriginUrl();
    }
}
