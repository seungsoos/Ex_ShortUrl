package org.example.shorturl.domain.repository;

import org.example.shorturl.domain.entity.UrlInfoEntity;
import org.example.shorturl.domain.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UrlInfoRepository extends JpaRepository<UrlInfoEntity, Long> {

    boolean existsByUrlEntity(UrlEntity urlEntity);

}
