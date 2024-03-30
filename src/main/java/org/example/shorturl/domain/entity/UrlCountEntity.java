package org.example.shorturl.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "TB_URL_COUNT")
@Getter
public class UrlCountEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUNT_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "URL_ID", nullable = false)
    private UrlEntity urlEntity;

    @Column(name = "COUNT", nullable = false)
    private Long count;
}
