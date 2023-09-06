## 정보

- 팀 이름 : 그랜드 부다페스트 호텔
- 프로젝트 명 : Hotel - Reservation
- 조장 : 오진석
- 부조장 : 김채림
- 팀원 : 박지호, 서지원, 김태현, 호지원, 이다은

## 주제

- 온라인 객실 예약 사이트

## BackEnd

- Java Version : JDK 17
- Build Type : Gradle(Groovy)
- Spring Version : 5.x.x
- Spring Boot Version : 3.x.x
- Denpendency
    - Java, Spring
        - Lombok
        - Spring Configuration Processor
        - Vaildation
        - SpringSecurity
        - JsonWebToken
    - DB
        - SpringDataJpa
        - SpringDataJdbc
        - H2DBDriver
        - MysqlDBDriver
        - log4jdbc
    - Web
        - Spring Web
        - Thymeleaf
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
- HTML (Figma)
- CSS (Figma)

## 문서/협업

- Notion
- Github
- Slack

## 기능 구성

1. 회원 기능
    1. 회원 가입

       +α Write Skew, Phantom Read 해결하기

       Ref 1, https://lion-king.tistory.com/entry/SpringJPATransaction-Write-skew-Phantom-1

       Ref 2, https://sabarada.tistory.com/175

    2. 회원 탈퇴
    3. 로그인

       Spring Security 적용하기

       JWT 적용하기

    4. 로그아웃
2. 객실 예약 기능
    1. 객실 예약
        - 객실 할인 정책
            - 숙박일 따른 객실 가격 할인 정책
                - 정률 할인
                - 정액 할인
            - 성수기 유무에 따른 객실 가격 할인 정책
                - 정률 할인
                - 정액 할인

       객실 정보

        - 인원 수
        - 침대 종류
        - 가격
    2. 예약 취소

   +α 변경 가능 조건을 만족 할 때 예약 변경 기능 (예약 날짜, 입실 시간 등)

   +α 예약 객체가 생성되면 회원 정보의 이메일로 예약 정보를 발송

   +α 객실 예약시 결제

3. 온라인 예약 확인 시스템
    1. 예약 목록
    2. 상세 예약
    3. 예약 취소

   +α 예약 변경 가능 여부 확인 (예약 날짜, 입실 시간 등을 고려)


## 디비 더미 데이터 및 설계 초안

### 호텔A 의 객실정보

규모

- 주소 : OO시 OO구 OO로 OO길 OO-OO
- 입실 시간 : 15:00
- 퇴실 시간 : 11:00
- 성수기 : 07-15~09-15

### 객실 정보

- 객실 타입
    - A Type - 슈퍼 싱글 베드 1~2인실
    - B Type - 트윈 베드 2인실
    - C Type - 퀸 베드 2~3인실
- 객실 호수
- 객실 1박 가격
- 호텔별 객실 타입별 수량

### 호텔B 의 객실 정보

### 호텔 정보

- 주소 : XX시 XX구 XX로 XX길 XX-XX
- 입실 시간 : 14:00
- 퇴실 시간 : 10:00
- 성수기 : 11-15 ~ 01-15

### 객실 정보

- 객실 타입
    - A Type - 슈퍼 싱글 베드 1~2인실
    - B Type - 트윈 베드 2인실
    - C Type - 퀸 베드 2~3인실
    - D Type - 킹 베드 4~5인실
- 객실 호수
- 객실 1박 가격
- 호텔별 객실 타입별 수량

### 가상의 회원 정보

### 회원 정보

- 이름 : 오진석
- 이메일 : abc1234@naver.com
- 비밀번호 : *******
- 전화번호 : 010-1234-5678
- 가입 상태
    - ACTIVE
    - DEACTIVE

### 호텔 A의 객실 B에 대한 회원 C의 예약정보

- 예약자 명 : 오진석
- 예약자 전화번호 : 010-1234-5678
- 호텔명 : 호텔 A
- 객실 종류 : A-Single
- 입실 시간 : 15:00
- 퇴실 시간 : 11:00
- 예약 날짜
    - 입실 날짜 : 2023.05.23.15:00.
    - 퇴실 날짜 : 2023.06.01.11:00.
- 예약 번호:  AB1-230523

  [호텔명 + 객실종류 +예약 순서+ 객실 번호 + 입실 년,월,일]

  예약 순서 → 타입별 전체 객실 수 - 타입별 남은 객실 수  = 타입별 예약 순서

- 결제 금액 : 1,000,000 원

  [할인된 1인 당 1박 금액 / 할인된 총 금액]

![DB초안](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/ba9e529f-20a6-4e56-a13a-1c02442fdb0c)

객체 설계 초안

![class초안](https://github.com/ojs258/Spring_TIL/assets/62832081/de7da95c-705d-414d-a5ed-19a43c0474cb)

엔티티 설계 초안

![Entity초안](https://github.com/ojs258/Spring_TIL/assets/62832081/3088d28e-5ee8-45c8-aa9b-75bacf8aa2ac)

메서드 설계 진행 중

![method초안](https://github.com/ojs258/Spring_TIL/assets/62832081/860bdd94-b638-4a9d-9670-4d70b5ac0726)

+api설계

역할분담

![roleAll](https://github.com/ojs258/Spring_TIL/assets/62832081/9f8ba35a-ae07-4799-8404-4816f90872fd)

+α 오류 코드 설계
