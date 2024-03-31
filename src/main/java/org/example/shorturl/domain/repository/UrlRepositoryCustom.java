package org.example.shorturl.domain.repository;

import org.example.shorturl.domain.dto.response.GetAllUrlsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UrlRepositoryCustom {

    Page<GetAllUrlsResponse> findAllByUrl(Pageable pageable);
}
