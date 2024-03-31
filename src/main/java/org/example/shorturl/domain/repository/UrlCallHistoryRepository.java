package org.example.shorturl.domain.repository;

import org.example.shorturl.domain.entity.UrlCallHistoryEntity;
import org.example.shorturl.domain.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlCallHistoryRepository extends JpaRepository<UrlCallHistoryEntity, Long> {

    List<UrlCallHistoryEntity> findByUrlEntity(UrlEntity urlEntity);
}
