package org.example.shorturl.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetDetailUrlResponse {

    private String shortUrl;
    private LocalDateTime createdDate;
}
