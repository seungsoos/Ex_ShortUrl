package org.example.shorturl.domain.dto.response;

import lombok.Getter;
import org.example.shorturl.domain.entity.UrlEntity;

import java.time.LocalDateTime;

@Getter
public class GetAllUrlsResponse {

    private Long urlId;
    private String originUrl;
    private String shortUrl;
    private LocalDateTime createdDate;
    private Long count;

    public GetAllUrlsResponse(UrlEntity urlEntity, Long count) {
        this.urlId = urlEntity.getId();
        this.originUrl = urlEntity.getOriginUrl();
        this.shortUrl = urlEntity.getShortUrl();
        this.createdDate = urlEntity.getCreatedDate();
        this.count = count;
    }
}
