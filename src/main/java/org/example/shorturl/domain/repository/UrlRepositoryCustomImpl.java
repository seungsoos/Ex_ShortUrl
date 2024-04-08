package org.example.shorturl.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.shorturl.domain.dto.response.GetAllUrlsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.shorturl.domain.entity.QUrlInfoEntity.urlInfoEntity;
import static org.example.shorturl.domain.entity.QUrlEntity.urlEntity;

@Repository
@RequiredArgsConstructor
public class UrlRepositoryCustomImpl implements UrlRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GetAllUrlsResponse> findAllByUrl(Pageable pageable) {
        List<GetAllUrlsResponse> content = queryFactory
                .select(Projections.constructor(GetAllUrlsResponse.class,
                        urlEntity.id,
                        urlEntity.originUrl,
                        urlEntity.shortUrl,
                        urlInfoEntity.count,
                        urlEntity.createdDate
                ))
                .from(urlEntity)
                .leftJoin(urlEntity.urlInfoEntity, urlInfoEntity)
                .on(urlEntity.id.eq(urlInfoEntity.urlEntity.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long countQuery = queryFactory
                .select(urlEntity.count())
                .from(urlEntity)
                .leftJoin(urlEntity.urlInfoEntity, urlInfoEntity)
                .on(urlEntity.id.eq(urlInfoEntity.urlEntity.id))
                .fetchOne();


        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery);
    }
}
