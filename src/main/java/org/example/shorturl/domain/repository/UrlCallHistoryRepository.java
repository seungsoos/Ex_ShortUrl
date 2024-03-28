package org.example.shorturl.domain.repository;

import org.example.shorturl.domain.entity.UrlCallHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlCallHistoryRepository extends JpaRepository<UrlCallHistoryEntity, Long> {
}
