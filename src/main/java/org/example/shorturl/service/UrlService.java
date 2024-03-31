package org.example.shorturl.service;

import org.example.shorturl.domain.dto.request.CreateShortUrlRequest;
import org.example.shorturl.domain.dto.response.GetAllUrlsResponse;
import org.example.shorturl.domain.dto.response.GetDetailUrlResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UrlService {

    Page<GetAllUrlsResponse> getAllUrls(Integer viewPage, Integer viewCount);

    List<GetDetailUrlResponse> getDetailUrl(Long id);

    String createShortUrl(CreateShortUrlRequest createShortUrlRequest);

    String redirectShortUrl(String shortUrl);

}
