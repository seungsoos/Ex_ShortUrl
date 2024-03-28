package org.example.shorturl.controller;

import lombok.RequiredArgsConstructor;
import org.example.shorturl.service.UrlService;
import org.example.shorturl.service.UrlServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/all")
    public void getAllUrls() {
        urlService.getAllUrls();
    }
}
