package org.example.shorturl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.common.exception.RootException;
import org.example.shorturl.domain.dto.request.CreateShortUrlRequest;
import org.example.shorturl.domain.dto.response.GetAllUrlsResponse;
import org.example.shorturl.domain.dto.response.GetDetailUrlResponse;
import org.example.shorturl.domain.entity.UrlCallHistoryEntity;
import org.example.shorturl.domain.entity.UrlInfoEntity;
import org.example.shorturl.domain.entity.UrlEntity;
import org.example.shorturl.domain.repository.UrlCallHistoryRepository;
import org.example.shorturl.domain.repository.UrlInfoRepository;
import org.example.shorturl.domain.repository.UrlRepository;
import org.example.shorturl.util.Base62Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UrlInfoRepository urlInfoRepository;
    private final UrlCallHistoryRepository urlCallHistoryRepository;
    private final Base62Util base62Util;
    private final String REDIRECT_URL = "http://localhost:8080/redirect/";


    @Override
    @Transactional(readOnly = true)
    public Page<GetAllUrlsResponse> getAllUrls(Integer viewPage, Integer viewCount) {
        Pageable pageable = PageRequest.of(viewPage, viewCount);

        return urlRepository.findAllByUrl(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetDetailUrlResponse> getDetailUrl(Long id) {
        UrlEntity urlEntity = urlRepository.findById(id)
                .orElseThrow(() -> new RootException("존재하지않는 URL 정보입니다."));

        List<UrlCallHistoryEntity> urlCallHistoryEntities = urlCallHistoryRepository.findByUrlEntity(urlEntity);
        return urlCallHistoryEntities.stream()
                .map(UrlCallHistoryEntity::toGetDetailUrlResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String createShortUrl(CreateShortUrlRequest createShortUrlRequest) {
        String originUrl = createShortUrlRequest.getOriginUrl();
        boolean exists = urlRepository.existsByOriginUrl(originUrl);
        isUrlExists(exists);

        UrlEntity urlEntity = new UrlEntity(originUrl);
        UrlEntity saveUrlEntity = urlRepository.save(urlEntity);

        String shortUrl = REDIRECT_URL + base62Util.encode(saveUrlEntity.getId());
        saveUrlEntity.setShortUrl(shortUrl);

        UrlInfoEntity urlInfoEntity = new UrlInfoEntity(urlEntity);
        urlInfoRepository.save(urlInfoEntity);
        
        log.info("saveUrlEntity.getId = {}, shortUrl = {}", saveUrlEntity.getId(), shortUrl);
        return shortUrl;
    }

    @Override
    @Transactional
    public String redirectShortUrl(String shortUrl) {
        String redirectUrl = REDIRECT_URL + shortUrl;
        System.out.println("redirectUrl = " + redirectUrl);
        UrlEntity urlEntity = urlRepository.findByShortUrl(redirectUrl)
                .orElseThrow(() -> new RootException("존재하지않는 URL 정보입니다."));

        UrlInfoEntity urlInfoEntity = urlEntity.getUrlInfoEntity();
        urlInfoEntity.increaseCount();

        UrlCallHistoryEntity urlCallHistoryEntity = new UrlCallHistoryEntity(urlEntity);
        urlCallHistoryRepository.save(urlCallHistoryEntity);
        return urlEntity.getOriginUrl();
    }

    private void isUrlExists(boolean result) {
        if (result) {
            throw new RootException("이미 존재하는 URL 입니다.");
        }
    }
}
