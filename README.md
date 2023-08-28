# playdata spring mini project

## 주제

온라인 예약 사이트

방향 1. 티켓 예약

## BackEnd

- Java Version : JDK 17
- Build Type : Gradle(Groovy)
- Spring Version : 5.x.x
- Spring Boot Version : 3.x.x
- Denpendency
    - Java, Spring
        - Lombok
        - Spring Configuration
        - Vaildation
    - DB
        - SpringDataJpa
        - SpringDataJdbc
        - H2DBDriver
        - MysqlDBDriver
    - Web
        - Spring Web
- DataBase
    - Main
        - MySql
    - Test
        - H2 Database
- 디자인패턴
    - 모놀리스 아키텍처(Monolithic Architecture) (단일 DB활용)
    - 모델-뷰-컨트롤러 패턴 (MVC, Model-View-Controller)
    - Service 패턴
    - Repository 패턴

## FrontEnd

- Thymeleaf
- Html
- CSS (BootStrap)

## 문서/협업

- Notion
- Github
    
    [Github Organization Manual](https://www.notion.so/Github-Organization-Manual-8f4f77e8bb024babb3a2a071dab3036c?pvs=21)
    
- Slack

## 기능 구성

1. 회원 기능
    1. 회원 가입
    2. 회원 탈퇴
    3. 로그인
    4. 로그아웃
2. 객실 예약 기능
    1. 객실 예약
    2. 예약 취소
    3. +a 변경 가능 할 시 변경 기능 (예약 날짜, 입실 시간 등)
3. 온라인 예약 확인 시스템
    1. 예약 목록
    2. 상세 예약
    3. 예약 취소
    4. +a 예약 변경 가능 여부 확인 (예약 날짜, 입실 시간 등)
