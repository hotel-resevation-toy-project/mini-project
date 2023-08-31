# playdata spring mini project

## 주제

온라인 객실 예약 사이트

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
        - log4jdbc
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
- Slack

## 기능 구성

1. 회원 기능
    1. 회원 가입

       +α Write Skew, Phantom Read 해결하기

    2. 회원 탈퇴
    3. 로그인

       +α Spring Security 적용하기

       +α OAuth2 적용하기

    4. 로그아웃
2. 객실 예약 기능
    1. 객실 예약

       객실 정보

        - 인원 수
        - 침대 종류
        - 층
    2. 예약 취소

   +α 변경 가능 조건을 만족 할 때 예약 변경 기능 (예약 날짜, 입실 시간 등)

   +α 예약 객체가 생성되면 회원 정보의 이메일로 예약 정보를 발송

   +α 객실 예약시 결제

3. 온라인 예약 확인 시스템
    1. 예약 목록
    2. 상세 예약
    3. 예약 취소

   +α 예약 변경 가능 여부 확인 (예약 날짜, 입실 시간 등을 고려)


+디비 설계 (진행중)

### 가상의 호텔, 객실 정보

### 호텔A 의 객실정보

규모

- 주소 : OO시 OO구 OO로 OO길 OO-OO
- 입실 시간 : 15:00
- 퇴실 시간 : 11:00
- 객실 정보
    - A Type - 트윈 베드 2인실
    - B Type -  퀸 베드 2~3인실
    - C Type - 슈퍼 싱글 베드 1~2인실

### 호텔B 의 객실 정보

### 호텔 정보

- 주소 : XX시 XX구 XX로 XX길 XX-XX
- 입실 시간 : 14:00
- 퇴실 시간 : 10:00
- 객실 정보
    - A Type - 트윈 베드 2인실
    - B Type -  퀸 베드 2~3인실
    - C Type - 슈퍼 싱글 베드 1~2인실
    - D Type - 킹 베드 4~5인실

### 가상의 회원 정보

### 회원 정보

- 이름 : 오진석
- 이메일 : abc1234@naver.com
- 비밀번호 : *******
- 전화번호 : 010-1234-5678

### 호텔 A의 객실 B에 대한 회원 C의 예약정보

- 예약자 명 : 오진석
- 예약자 전화번호 : 010-1234-5678
- 호텔명 : 호텔 A
- 객실명 : B-2 싱글 베드 1인실
- 입실 시간 : 15:00
- 예약 날짜 : 2023-05-23 ~ 2023-06-01
- 예약 번호:  AB2-0523

ERD 설계 초안 완료 - 23-08-30

+객체 설계

+메서드 설계

+api설계

+α 오류 코드 설계