package org.example.shorturl.service;

import org.example.shorturl.domain.dto.request.CreateShortUrlRequest;
import org.example.shorturl.domain.dto.response.GetAllUrlsResponse;
import org.example.shorturl.domain.enums.UrlType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UrlService {

    Page<GetAllUrlsResponse> getAllUrls(Integer viewPage, Integer viewCount);

    void getDetailUrl(Long id);

    String createShortUrl(CreateShortUrlRequest createShortUrlRequest);

    String redirectShortUrl(String shortUrl);

}
