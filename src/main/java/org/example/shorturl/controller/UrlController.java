package org.example.shorturl.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.shorturl.domain.dto.request.CreateShortUrlRequest;
import org.example.shorturl.domain.dto.response.GetAllUrlsResponse;
import org.example.shorturl.domain.enums.UrlType;
import org.example.shorturl.service.UrlService;
import org.springframework.data.domain.Page;
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
     */
    @GetMapping("/all")
    public ResponseEntity<Page<GetAllUrlsResponse>> getAllUrls(
            @RequestParam(value = "viewPage", defaultValue = "0") Integer viewPage
            , @RequestParam(value = "viewCount", defaultValue = "20") Integer viewCount
    ) {
        Page<GetAllUrlsResponse> getAllUrlsResponses = urlService.getAllUrls(viewPage, viewCount);
        return ResponseEntity.ok().body(getAllUrlsResponses);
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
