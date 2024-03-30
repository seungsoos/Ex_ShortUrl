package org.example.shorturl.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateShortUrlRequest {

    @NotBlank
    @Pattern(regexp = "^(https?://).*", message = "URL 입력 형식에 맞지않습니다.")
    private String originUrl;
}
