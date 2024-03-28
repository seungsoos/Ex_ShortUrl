package org.example.shorturl.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_URL")
@Getter
public class UrlEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "URL_ID")
    private Long id;

    @Column(name = "ORIGIN_URL", nullable = false, unique = true)
    private String originUrl;

    @Column(name = "SHORT_URL", nullable = false, unique = true)
    private String shortUrl;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "urlEntity", cascade = CascadeType.ALL)
    private UrlCountEntity urlCountEntity;

    @OneToMany(mappedBy = "urlEntity")
    private List<UrlCallHistoryEntity> urlCallHistoryEntities = new ArrayList<>();
}
