# [Toy] ShortUrl 제공 서비스

## 1. 프로젝트 소개
* URL 입력시 ShortUrl로 변환하고, ShortUrl 입력시 원본 URL로 Redirect 하는 서비스

## 2. 기술스택
* Java, Spring Boot, Jpa, QueryDsl, Junit, mySql(Docker), Redis

## 3. 구현 기능

<img width="1466" alt="image" src="https://github.com/seungsoos/Toy_ShortUrl/assets/113499796/ed298ca8-be59-4851-9f61-97e2f5e2b45a">


* URL 변환
  * 원본 URL을 입력하여 Base62로 인코딩하여 ShortUrl을 반환
* URL 상세 보기
  * URL 호출 History 조회
* URL 전체 조회
  * URL 정보 및 Count 조회
---
* URL Redirect
  * hortUrl을 입력받아 원본 URL로 Redirect 후 URL 호출 Count를 1증가 시키고, History Table에 저장

## 4. ERD
* TB_URL : URL 기본 정보 테이블
* TB_URL_INFO : 확장성을 고려하여 URL 정보 저장 테이블
* TB_URL_CALL_HISTORY: URL 호출이력 히스토리 저장 테이블

![image](https://github.com/seungsoos/Toy_ShortUrl/assets/113499796/5359f970-9aa4-4ea9-b6d4-b0acfd41eb4a)

## 패키지 구성

```
📂 ─ common
│
📂 ─ config
│
📂 ─ controller
│
📂 ─ domain
│
📂 ─ service
│
📂 ─ util
```


