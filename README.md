# [Toy] ShortUrl 제공 서비스

## 1. 프로젝트 소개
* URL 입력시 ShortUrl로 변환하고, ShortUrl 입력시 원본 URL로 Redirect 하는 서비스

## 2. 기술스택
* Java, Spring Boot, Jpa, QueryDsl, Junit, mySql(Docker), Redis

## 3. 구현 기능
* URL 전체 조회
  * URL 정보 및 Count 조회
* URL 상세 보기
  * URL 호출 History 조회
* URL 변환
  * 원본 URL을 입력하여 Base62로 인코딩하여 ShortUrl을 반환
* URL Redirect
  * hortUrl을 입력받아 원본 URL로 Redirect 후 URL 호출 Count를 1증가 시키고, History Table에 저장

## 4. ERD


<!-- 

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
-->

