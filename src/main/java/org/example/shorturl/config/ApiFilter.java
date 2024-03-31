package org.example.shorturl.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        printLogRequestAndResponse(requestWrapper, responseWrapper,request);
        responseWrapper.copyBodyToResponse();
    }

    private void printLogRequestAndResponse(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper, HttpServletRequest request) {

        String uuid = UUID.randomUUID().toString();

        try {
            if (requestWrapper.getContentType() != null && requestWrapper.getContentType().contains("application/json")) {
                JsonNode requestJson = this.objectMapper.readTree(requestWrapper.getContentAsByteArray());
                log.info("> [UUID] : {} [URI] : {} [RequestJson] : {}", uuid, requestWrapper.getRequestURI(), requestJson);
            } else {
                log.info("> [UUID] : {} [URI] : {}", uuid, requestWrapper.getRequestURI());
            }
            if (responseWrapper.getContentType() != null && responseWrapper.getContentType().contains("application/json")) {
                JsonNode responseJson = this.objectMapper.readTree(responseWrapper.getContentAsByteArray());
                log.info("> [UUID] : {} [URI] : {} [ResponseJson] : {}", uuid, requestWrapper.getRequestURI(), responseJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
