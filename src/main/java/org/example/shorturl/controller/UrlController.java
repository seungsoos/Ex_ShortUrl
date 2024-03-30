package org.example.shorturl.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shorturl.domain.dto.request.CreateShortUrlRequest;
import org.example.shorturl.domain.dto.response.GetAllUrlsResponse;
import org.example.shorturl.domain.enums.UrlType;
import org.example.shorturl.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;

    /**
     * URL 전체조회
     * TYPE - ALL / ORIGIN / SHORT
     */
    @GetMapping("/all")
    public List<GetAllUrlsResponse> getAllUrls(@RequestParam UrlType urlType) {
        List<GetAllUrlsResponse> allUrls = urlService.getAllUrls(urlType);
        return allUrls;
    }

    /**
     * URL 상세조회
     */
    @GetMapping("/detail/{id}")
    public void getDetailUrl(@PathVariable Long id) {

    }

    /**
     * URL 변환
     * originalUrl -> shortUrl
     */
    @PostMapping
    public ResponseEntity<String> createShortUrl(@RequestBody @Valid CreateShortUrlRequest createShortUrlRequest) {
        String shortUrl = urlService.createShortUrl(createShortUrlRequest);
        return ResponseEntity.ok().body(shortUrl);
    }




}
