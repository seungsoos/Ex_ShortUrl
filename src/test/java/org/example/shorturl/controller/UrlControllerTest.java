package org.example.shorturl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.shorturl.domain.dto.request.CreateShortUrlRequest;
import org.example.shorturl.service.UrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = UrlController.class)
class UrlControllerTest {


    @Autowired
    MockMvc mockMvc;
    @MockBean
    UrlService urlService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @DisplayName("URL 변환 요청시 http와 https 형식을 허용한다.")
    void originUrl_OK() throws Exception {
        // given - when
        String httpOriginUrl = "http://www.naver.com/";
        String httpsoriginUrl = "https://www.naver.com/";

        CreateShortUrlRequest createShortUrlRequest1 = CreateShortUrlRequest.builder()
                .originUrl(httpOriginUrl)
                .build();
        CreateShortUrlRequest createShortUrlRequest2 = CreateShortUrlRequest.builder()
                .originUrl(httpsoriginUrl)
                .build();

        // then
        mockMvc.perform(post("/url")
                        .content(objectMapper.writeValueAsString(createShortUrlRequest1))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(post("/url")
                        .content(objectMapper.writeValueAsString(createShortUrlRequest2))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @DisplayName("URL 변환 요청시 http와 https 형식이 아니면 허용하지 않는다.")
    void originUrl_NOT_OK() throws Exception {
        // given - when
        String originUrl = "www.naver.com/";

        CreateShortUrlRequest createShortUrlRequest = CreateShortUrlRequest.builder()
                .originUrl(originUrl)
                .build();

        // then
        mockMvc.perform(post("/url")
                        .content(objectMapper.writeValueAsString(createShortUrlRequest))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

}