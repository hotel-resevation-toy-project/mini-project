# Hotel Reservation Project

## 정보

- 팀 이름 : 그랜드 부다페스트 호텔
- 프로젝트 명 : Hotel - Reservation
- 조장 : 오진석
- 부조장 : 김채림
- 팀원 : 서지원, 김태현, 호지원, 이다은

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
        - H2DBDriver
        - MysqlDBDriver
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
- HTML
- CSS (bootstrap)

## 문서/협업

- Notion
- Github
- Slack

## 기능 구성

1. 회원 기능
    1. 회원 가입
        - 중복 가입, 재 가입 체크 로직
        - 팬텀리드는 이메일을 아이디로 사용해서 해결
        - 권한 부여 로직
    2. 회원 탈퇴
        - 가입 상태 컬럼을 둬서 사용자 입장에서는 탈퇴 DB입장에서는 비활성화
    3. 로그인
        - Spring Security와 JWT를 적용해 보안 강화
    4. 로그아웃
2. 호텔 호스트기능
    1. 호텔 정책 변경 기능
        - 적용될 할인 정책 종류 변경
            - 성수기, 연박, 전체
        - 객실 문제 발생시 예약 가능 객실 수 차감
        - 호텔별 예약 목록 확인
        - 객실 타입별 금액 책정
3. 객실 예약 기능
    1. 객실 예약
        - 객실 할인 정책
            - 숙박일 따른 객실 가격 할인 정책
                - 3일 이상 부터 3일에 한 번씩 할인이 적용
                - 정률 할인 5% (int)(Math.pow(원가 * 0.95, 일 수 / 3))
                - 정액 할인 3천 (원가 - 일 수 / 3 * 3000)
            - 성수기 유무에 따른 객실 가격 할인 정책
                - 정률 할인 30%
                - 정액 할인
                  원가가 30만 이상 일 때 10만원 할인
        - 객실 정보
            - 인원 수
            - 침대 종류
            - 가격
4. 온라인 예약 확인 시스템
    1. 예약 목록
    2. 상세 예약
    3. 예약 취소
        - 입실 날짜 5일전 까지만 취소 가능

## 디비 더미 데이터 및 설계

### 호텔A 의 객실정보

규모

- 주소 : OO시 OO구 OO로 OO길 OO-OO
- 입실 시간 : 15:00
- 퇴실 시간 : 11:00
- 성수기 : 07-15~09-15
- 할인 정책 : 성수기 할인, 연박 할인, 두 정책 다 적용

### 객실 정보

- 객실 타입
    - A Type - 슈퍼 싱글 베드 1~2인실
        - 15만
    - B Type - 트윈 베드 2인실
        - 20만
    - C Type - 퀸 베드 2~3인실
        - 30만
- 객실당 가격
- 호텔별 객실 타입별 수량

### 호텔B 의 객실 정보

### 호텔 정보

- 주소 : XX시 XX구 XX로 XX길 XX-XX
- 입실 시간 : 14:00
- 퇴실 시간 : 10:00
- 성수기 : 11-15 ~ 01-15
- 할인 정책 : 성수기 할인, 연박 할인, 두 정책 다 적용

### 객실 정보

- 객실 타입
    - A Type - 슈퍼 싱글 베드 1~2인실
        - 15만
    - B Type - 트윈 베드 2인실
        - 20만
    - C Type - 퀸 베드 2~3인실
        - 30만
    - D Type - 킹 베드 4~5인실
        - 50만
- 객실당 가격
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
    - 로그인, 재 회원가입시 체크해야함
- 관리자 계정 여부
    - User (사용자)
    - Host (호텔)

### 호텔 A의 객실 B에 대한 회원 C의 예약정보

- 예약자 명 : 오진석
- 예약자 전화번호 : 010-1234-5678
- 호텔명 : 호텔 A
- 객실 종류 : A-Single
- 예약 날짜
    - 입실 날짜 : 2023.05.23.15:00.
    - 퇴실 날짜 : 2023.06.01.11:00.
- 예약 번호: AA1-230523

  [호텔명 + 객실종류 +예약 순서 + 입실 년,월,일]

  예약 순서 → 타입별 전체 객실 수 - 타입별 남은 객실 수 = 타입별 예약 순서

- 결제 금액 : 1,050,000원

![DB설계](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/91fc5323-73d4-4226-af63-7444b83623cd)

## 엔티티 설계

![Entity설계](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/4bee2ea6-a64a-40bf-a8a1-3c1c4dbf83e0)

## 객체 설계

![Class설계](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/8c2c149d-f80b-4cd0-8b18-f5a14891a27f)

## 메서드 설계

### 유저 메서드

![UserMethod설계](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/0123c1f8-8952-40bd-a1b0-855014f9451a)

### 호스트 메서드

![HostMethod설계](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/dedeb998-6222-4b9d-8dd9-dd6b2e12036e)

### 예약 메서드

![Reservation설계](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/5052b459-d397-408e-a308-040bd4e43025)

### JWT토큰 관련 메서드

![TokenMethod설계](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/45bd21d1-ec29-46a3-9df4-0b39aca5b5ea)

### 역할분담

![Class설계-Role](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/0574490a-856b-4f5f-a878-89111dddc1f9)

### api설계 완료

- GET : 조회
- POST : 대부분의 기능을 다 할 수 있지만 등록
- PUT : 전체 수정
- PATCH : 일부 수정
- DELETE : 삭제
- UserApiController

| METHOD | KEYWORDS | PARAMATER | NOTE |
| --- | --- | --- | --- |
| GET | /user/new |  | 회원가입 폼 |
| POST | /user/new | SignUpDto(JsonBody) | 회원가입(회원 정보) |
| POST | /user | SignInDto(JsonBody) | 로그인(로그인 정보) |
| GET | /user |  | 로그아웃 |
| PATCH | /user |  | 회원탈퇴 |
| PUT | /user | UserDto(JsonBody) | 업데이트(변경 정보) |
- HostApiController

| METHOD | KEYWORDS | PARAMATER | NOTE |
| --- | --- | --- | --- |
| GET | /host |  | HOST페이지 |
| PATCH | /host/policy | /{discountPolicy} | 할인정책 변경 |
| PATCH | /host/price | /{roomType}&{price} | 객실 타입별 가격변경 |
| PATCH | /host/plus | /{roomType} | 이용 가능 객실 수 + |
| PATCH | /host/minus | /{roomType} | 이용 가능 객실 수 - |
| GET | /host/reservations |  | 호텔별 예약 전체 조회 |
- ReserveApiController

| METHOD | KEYWORDS | PARAMATER | NOTE |
| --- | --- | --- | --- |
| GET | /reservation |  | 예약 폼 |
| POST | /reservation | ReservationDto(JsonBody) | 예약하기(예약 정보) |
| GET | /reservations |  | 회원별 예약 전체 조회 |
| GET | /reservation | /{reserveNumber} | 예약 상세 조회 |
| DELETE | /cancel | /{reserveNumber} | 예약 취소 |