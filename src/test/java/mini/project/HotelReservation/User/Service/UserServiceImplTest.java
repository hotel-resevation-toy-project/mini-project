package mini.project.HotelReservation.User.Service;

import com.sun.jdi.request.DuplicateRequestException;
import jakarta.servlet.http.HttpServletRequest;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Dto.UserInfoDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.enumerate.RoomType;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceImplTest {
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final TokenDecoder td;
    private final PasswordEncoder passwordEncoder;

    @Mocked
    HttpServletRequest mockRequest;
    @Autowired
    public UserServiceImplTest(UserServiceImpl userServiceImpl, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository, ReservationRepository reservationRepository, TokenDecoder td, PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.td = td;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void init(){
        td.init();
        mockRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        // 임의 유저
        User user = new User("Serah","sexy123@play.data",
                passwordEncoder.encode("123"), "123-4567-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_HOST);
        // 임의 호텔    -> 호텔 가입시
        Hotel hotel = new Hotel("신대방", "그부호",
                "010-1234-1234", DiscountPolicy.POLICY_DAYS,
                LocalTime.NOON, LocalTime.MIDNIGHT,
                LocalDate.now(), LocalDate.now().plusMonths(2));
        // 임의 객실    -> 예약 정보 확인
        Room room = new Room(RoomType.ROOM_TYPE_A_SINGLE, 100000,20);
        // 임의 예약
        Reservation reservation1 = new Reservation("AA1-230523",
                5000000, RoomType.ROOM_TYPE_A_SINGLE, "그부호"
                ,"010-2222-3333", "Serah",
                LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(5).atStartOfDay());
        Reservation reservation2 = new Reservation("AA1-430525",
                5000000, RoomType.ROOM_TYPE_A_SINGLE, "그부호"
                ,"010-4444-5555", "Grima",
                LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(5).atStartOfDay());
        Reservation reservation3 = new Reservation("AA1-630528",
                5000000, RoomType.ROOM_TYPE_A_SINGLE, "그부호"
                ,"010-6666-7777", "Mosquito",
                LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(5).atStartOfDay());

        room.foreignHotel(hotel);
        // 호스트에 호텔 주입
        hotel.foreignUser(user);
        userRepository.save(user);
        // 예약1
        reservation1.foreignHotel(hotel);
        reservation1.foreignUser(user);
        reservationRepository.save(reservation1);
        // 예약2
        reservation2.foreignHotel(hotel);
        reservation2.foreignUser(user);
        reservationRepository.save(reservation2);
        // 예약3
        reservation3.foreignHotel(hotel);
        reservation3.foreignUser(user);
        reservationRepository.save(reservation3);

        // 로그인된 host User를 Security Context에 저장
        User ckUser = userRepository.findById(userRepository.findAll().get(0).getUserId()).get();
        td.createToken(String.valueOf(ckUser.getRole()), String.valueOf(ckUser.getUserId()), String.valueOf(ckUser.getHotel().getHotelId()));
        SecurityContextHolder.getContext().setAuthentication(td.getAuthentication(td.resolveToken(mockRequest)));
    }

    @AfterEach
    public void reset(){
        hotelRepository.deleteAll();
        roomRepository.deleteAll();
        reservationRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    void 회원_가입_USER() {
        //given
        User user1 = new User("Serah","sexy123@play.data", "123", "123-4567-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_USER);
        //when, then
        Assertions.assertThat(user1.getName().equals("Serah"));
        Assertions.assertThat(user1.getEmail().equals("sexy123@play.data"));
    }
    @Test
    void 회원_가입_HOST() {
        //given
        User user1 = new User("Hotel_A","hi_Hotel@play.data", "1234", "123-4560-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_HOST);
        //when, then
        Assertions.assertThat(user1.getName().equals("Hotel_A"));
        Assertions.assertThat(user1.getEmail().equals("hi_Hotel@play.data"));
    }
    @Test
    void 중복_회원_가입() {
        //given
        UserSignUpDto sud = new UserSignUpDto("Serah","sexy123@play.data", "123", "123-4567-9101", UserRole.ROLE_USER);
        //when, then
        DuplicateRequestException exception = assertThrows(DuplicateRequestException.class, ()-> userServiceImpl.join(sud));
        String message = exception.getMessage();
        assertEquals("이미 가입한 사용자입니다.", message);
    }

    @Test
    void 탈퇴한_회원_재가입() {
        //given
        User user = userRepository.findByEmail("sexy123@play.data").get();
        user.deactive();
        UserSignUpDto sud = new UserSignUpDto("Serah","sexy123@play.data", "123", "123-4567-9101", UserRole.ROLE_USER);
        userServiceImpl.join(sud);
        //when, then
        Assertions.assertThat(user.getStatus().equals(UserStatus.USER_STATUS_ACTIVE));
    }

    @Test
    void 로그인() {
        //given
        User user = userRepository.findByEmail("sexy123@play.data").get();
        UserSignInDto sid = new UserSignInDto(user.getEmail(),"123");
        userServiceImpl.logIn(sid);
        //when, then
        Assertions.assertThat(sid.getEmail().equals("sexy123@play.data"));
    }
    @Test
    void 비회원_로그인(){
        //given
        UserSignInDto s = new UserSignInDto("abc@naver.com","456");
        //when, then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, ()-> userServiceImpl.logIn(s));
        String message = exception.getMessage();
        assertEquals("회원을 찾을 수 없습니다.", message);
    }
    @Test
    void 탈퇴한_회원_로그인(){
        //given
        User user = userRepository.findByEmail("sexy123@play.data").get();
        user.deactive();
        UserSignInDto sid = new UserSignInDto(user.getEmail(), user.getPassword());
        //when, then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, ()-> userServiceImpl.logIn(sid));
        String message = exception.getMessage();
        assertEquals("회원을 찾을 수 없습니다.", message);
    }

    @Test
    void 정보_업데이트() {
        //given
        User user = userRepository.findByEmail("sexy123@play.data").get();
        UserInfoDto dto = new UserInfoDto("김채림", "abc@naver.com","456","010-5607-7854");
        user.updateInfo(dto);
        //when, then
        Assertions.assertThat(user.getName().equals("김채림"));
        Assertions.assertThat(user.getEmail().equals("abc@naver.com"));
        Assertions.assertThat(user.getPassword().equals("456"));
        Assertions.assertThat(user.getPhoneNumber().equals("010-5607-7854"));
    }

    //todo : 시큐리티에서 자동 로그인 작동 여부 확인 필요 -> 컨트롤러 작성 후
    @Test
    void 로그아웃(){

    }
}