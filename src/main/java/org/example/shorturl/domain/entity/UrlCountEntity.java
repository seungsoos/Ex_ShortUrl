package org.example.shorturl.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "TB_URL_COUNT")
@Getter
public class UrlCountEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "URL_COUNT_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "URL_ID")
    private UrlEntity urlEntity;
}
