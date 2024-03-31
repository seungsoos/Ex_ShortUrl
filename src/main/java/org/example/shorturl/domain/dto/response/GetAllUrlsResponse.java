package org.example.shorturl.domain.dto.response;

import lombok.Getter;
import org.example.shorturl.domain.entity.UrlEntity;

import java.time.LocalDateTime;

@Getter
public class GetAllUrlsResponse {

    private Long urlId;
    private String originUrl;
    private String shortUrl;
    private Long count;
    private LocalDateTime createdDate;

    public GetAllUrlsResponse(Long urlId, String originUrl, String shortUrl, Long count, LocalDateTime createdDate) {
        this.urlId = urlId;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.count = count;
        this.createdDate = createdDate;
    }
}
