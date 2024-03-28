package org.example.shorturl.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "TB_URL_CALL_HISTORY")
@Getter
public class UrlCallHistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "URL_CALL_HISTORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "URL_ID")
    private UrlEntity urlEntity;


}
