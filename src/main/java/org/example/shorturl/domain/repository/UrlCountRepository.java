package org.example.shorturl.domain.repository;

import org.example.shorturl.domain.entity.UrlCountEntity;
import org.example.shorturl.domain.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UrlCountRepository extends JpaRepository<UrlCountEntity, Long> {

    boolean existsByUrlEntity(UrlEntity urlEntity);

}
