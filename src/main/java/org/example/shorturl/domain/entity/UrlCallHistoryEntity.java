package org.example.shorturl.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.shorturl.domain.dto.response.GetDetailUrlResponse;

@Entity
@Table(name = "TB_URL_CALL_HISTORY")
@Getter
@NoArgsConstructor
public class UrlCallHistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "URL_CALL_HISTORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "URL_ID", nullable = false)
    private UrlEntity urlEntity;

    public UrlCallHistoryEntity(UrlEntity urlEntity) {
        this.urlEntity = urlEntity;
    }

    public GetDetailUrlResponse toGetDetailUrlResponse() {
        return new GetDetailUrlResponse(this.getUrlEntity().getShortUrl(), this.getCreatedDate());
    }
}
