package org.example.shorturl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.domain.repository.UrlCallHistoryRepository;
import org.example.shorturl.domain.repository.UrlCountRepository;
import org.example.shorturl.domain.repository.UrlRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService{

    private final UrlRepository urlRepository;
    private final UrlCountRepository urlCountRepository;
    private final UrlCallHistoryRepository urlCallHistoryRepository;

    public void getAllUrls() {
        log.info("getAllUrls");
    }
}
