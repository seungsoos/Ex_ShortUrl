package org.example.shorturl.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.service.UrlService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedissonLockFacade {

    private final RedissonClient redissonClient;
    private final UrlService urlService;

    public String redirectShortUrl(String redirectUrl) {
        RLock lock = redissonClient.getLock(redirectUrl);

        try {
            boolean result = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!result) {
                log.info("Lock 획득 실패");
                return null;
            }
            return urlService.redirectShortUrl(redirectUrl);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
