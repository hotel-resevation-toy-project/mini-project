# Hotel Reservation Project

생성자: 오진석
생성 일시: 2023년 9월 5일 오후 7:21

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
        - Vaildation v
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
- HTML
- CSS (bootstrap)

## 문서/협업

- Notion
- Github
- Slack

## 기능 구성

1. 회원 기능
    1. 회원 가입
        - 아이디 중복 검사 로직
        - 팬텀리드는 이메일을 아이디로 사용해서 해결
        - 권한 부여 로직
    2. 회원 탈퇴
        - 가입 상태 컬럼을 둬서 사용자 입장에서는 탈퇴 DB입장에서는 비활성화
    3. 로그인
        - Spring Security와 JWT를 적용해 보안 강화 및 호스트 역할 기능 구현
        - 토큰 유지 시간
    4. 로그아웃
2. 호텔 호스트기능
    1. 호텔 정책 변경 기능
        - 적용될 할인 정책 종류 변경
            - 성수기, 연박, 전체
        - 객실 문제 발생시 예약 가능 객실 수 차감
        - 호텔별 예약 목록 확인
        - 객실 타입별 금액 책정

          서비스 클래스 만들어서 구현

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

## 디비 더미 데이터 및 설계 초안

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
- 예약 번호:  AA1-230523

  [호텔명 + 객실종류 +예약 순서 + 입실 년,월,일]

  예약 순서 → 타입별 전체 객실 수 - 타입별 남은 객실 수  = 타입별 예약 순서

- 결제 금액 : 1,050,000원

![DB설계 최종](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/d491f317-f4ad-4243-8f3e-9460fb9895bc)

엔티티 설계 완료

![Entity설계 최종](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/00d4c871-92a1-415d-bae5-6f8329ca8bdc)

객체 설계 완료

![Class 설계 최종](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/5866b07b-0ac0-48b8-8dd1-534528fb2635)

메서드 설계 완료

- 유저 메서드

  ![UserMethod설계 최종](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/411ef42b-c9f2-4d2a-86f5-67e437d8930b)

- 호스트 메서드

  ![HostMethod설계 최종](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/66fc70b5-1c34-4026-a677-e5d1467c87ee)

- 예약 메서드

  ![ReserveMethod설계 최종](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/16875623-307d-4b5b-aea6-5ff2d742cb2e)


- JWT토큰 관련 메서드

  ![TokenMethod설계 최종](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/ea69c1a5-0a8a-4231-b944-5e73f4e26e95)


역할분담

![Role최종](https://github.com/hotel-resevation-toy-project/mini-project/assets/62832081/0e223a96-2747-499f-813b-25b09aa3c34f)

api설계 완료

- GET : 조회
- POST : 대부분의 기능을 다 할 수 있지만 등록
- PUT : 전체 수정
- PATCH : 일부 수정
- DELETE : 삭제
- UserApiController

| METHOD | KEYWORDS | PARAMATER | NOTE |
| --- | --- | --- | --- |
| POST | /new/user | SignUpDto(JsonBody) | 회원가입(회원 정보) |
| POST | /user | SignInDto(JsonBody) | 로그인(로그인 정보) |
| GET | /user |  | 로그아웃 |
| PATCH | /user |  | 회원탈퇴 |
| PUT | /user | UserDto(JsonBody) | 업데이트(변경 정보) |
| GET | /host |  | HOST페이지 |
- HostApiController

| METHOD | KEYWORDS | PARAMATER | NOTE |
| --- | --- | --- | --- |
| PATCH | /host/policy | /{discountPolicy} | 할인정책 변경 |
| PATCH | /host/price | /{roomType}/{price} | 객실 타입별 가격변경 |
| PATCH | /host/plus | /{roomType} | 이용 가능 객실 수 + |
| PATCH | /host/minus | /{roomType} | 이용 가능 객실 수 - |
| GET | /host/reservation |  | 호텔별 예약 전체 조회 |
- ReserveApiController

| METHOD | KEYWORDS | PARAMATER | NOTE |
| --- | --- | --- | --- |
| POST | /reservation | ReservationDto(JsonBody) | 예약하기(예약 정보) |
| GET | /reservations |  | 회원별 예약 전체 조회 |
| GET | /reservation | {reservId} | 예약 상세 조회 |
| DELETE | /cancel | {reservId} | 예약 취소 |