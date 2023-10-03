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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceImplTest {
    private final UserService userService;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final TokenDecoder td;
    private final PasswordEncoder passwordEncoder;

    @Mocked
    HttpServletRequest mockRequest;
    @Autowired
    public UserServiceImplTest(UserService userService, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository, ReservationRepository reservationRepository, TokenDecoder td, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.td = td;
        this.passwordEncoder = passwordEncoder;
    }

    @AfterEach
    public void reset(){
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
        hotelRepository.deleteAll();
    }

    @BeforeEach
    void init(){
        // 토큰 초기화 및 더미 요청 생성
        td.init();
        mockRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // 호스트 생성
        User host = new User("Hotel_A",
                "abc@example.com",
                "1234",
                "010-1234-5678",
                UserStatus.USER_STATUS_ACTIVE,
                UserRole.ROLE_HOST);
        // 유저 생성
        User user = new User("오진석",
                "abc2@example.com",
                passwordEncoder.encode("1234"),
                "010-1234-5678",
                UserStatus.USER_STATUS_ACTIVE,
                UserRole.ROLE_USER);
        userRepository.save(user);
        // 호텔 생성
        // A
        Hotel hotel = new Hotel("성북구",
                "Hotel_A",
                "02-123-4567",
                DiscountPolicy.POLICY_PEAK,
                LocalTime.of(13, 0, 0),
                LocalTime.of(10, 0, 0),
                LocalDate.now(),
                LocalDate.now().plusMonths(2));
        hotel.foreignUser(host);
        Hotel saveHotel = hotelRepository.save(hotel);
        // B
        Hotel hotelB = new Hotel("신대방",
                "Hotel_B",
                "02-123-4567",
                DiscountPolicy.POLICY_PEAK,
                LocalTime.of(13, 0, 0),
                LocalTime.of(18, 0, 0),
                LocalDate.now(),
                LocalDate.now().plusMonths(2));
        // 귀찮아서 얘는 호스트 없음
        Hotel saveHotelB = hotelRepository.save(hotelB);
        // 객실 생성
        // 호텔 A꺼
        Room roomA = new Room(RoomType.ROOM_TYPE_A_SINGLE, 100000, 10);
        roomA.foreignHotel(saveHotel);
        Room roomB = new Room(RoomType.ROOM_TYPE_B_TWIN, 200000, 20);
        roomB.foreignHotel(saveHotel);
        // 호텔 B꺼
        Room roomC = new Room(RoomType.ROOM_TYPE_C_QUEEN, 300000, 20);
        roomC.foreignHotel(saveHotelB);
        roomRepository.saveAll(List.of(roomA,roomB, roomC));
        // 예약 1, 2, 3 생성
        Reservation reservation1 = new Reservation("AA1-230523",
                3000000, RoomType.ROOM_TYPE_A_SINGLE, "Hotel_A"
                ,"010-2222-3333", "Serah",
                LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(5).atStartOfDay());
        reservation1.foreignUser(user);  reservation1.foreignHotel(hotel);
        Reservation reservation2 = new Reservation("AB1-430525",
                5400000, RoomType.ROOM_TYPE_B_TWIN, "Hotel_A"
                ,"010-4444-5555", "Grima",
                LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(5).atStartOfDay());
        reservation2.foreignUser(host);  reservation2.foreignHotel(hotel);
        Reservation reservation3 = new Reservation("BC1-630528",
                50034600, RoomType.ROOM_TYPE_C_QUEEN, "Hotel_B"
                ,"010-6666-7777", "Mosquito",
                LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(5).atStartOfDay());
        reservation3.foreignUser(user);  reservation3.foreignHotel(hotelB);
        reservationRepository.saveAll(List.of(reservation1, reservation2, reservation3));
    }
    @Test
    void 회원_가입_USER() {
        //given
        UserSignUpDto sud = new UserSignUpDto("Serah",
                "sexy123@play.data",
                "123",
                "123-4567-9101",
                UserRole.ROLE_USER);
        userService.join(sud);

        //when
        Optional<User> user = userRepository.findByEmail("sexy123@play.data");
        User findUser = user.get();

        //then
        assertThat(findUser.getName().equals("Serah"));
        assertThat(findUser.getEmail().equals("sexy123@play.data"));
    }
    @Test
    void 회원_가입_HOST() {
        //given
        UserSignUpDto sud = new UserSignUpDto("Hotel_B",
                "hotelB@hotel.com",
                "1234",
                "123-4567-9101",
                UserRole.ROLE_HOST);
        userService.join(sud);
        //when
        Optional<User> user = userRepository.findByEmail("hotelB@hotel.com");
        User findHost = user.get();
        //then
        assertThat(findHost.getName().equals("Hotel_B"));
        assertThat(findHost.getRole().equals(UserRole.ROLE_HOST));
    }
    @Test
    void 중복_회원_가입() {
        //given
        UserSignUpDto sud1 = new UserSignUpDto("Serah",
                "sexy123@play.data",
                "123",
                "123-4567-9101",
                UserRole.ROLE_USER);

        UserSignUpDto sud2 = new UserSignUpDto("Serah",
                "sexy123@play.data",
                "123",
                "123-4567-9101",
                UserRole.ROLE_USER);
        userService.join(sud1);
        //when, then
        assertThrows(DuplicateRequestException.class,() -> userService.join(sud2));
    }

    @Test
    void 탈퇴_후_재가입() {
        //given
        Optional<User> ckUser = userRepository.findByEmail("abc2@example.com");
        td.createToken(String.valueOf(ckUser.get().getRole()), String.valueOf(ckUser.get().getUserId()));
        SecurityContextHolder.getContext().setAuthentication(td.getAuthentication(td.resolveToken(mockRequest)));

        //when
        userService.deactive("1234");
        User user = td.currentUser();

        //then
        assertThat(user.getStatus().equals(UserStatus.USER_STATUS_DEACTIVE));

        //given
        UserSignUpDto sud = new UserSignUpDto("Serah",
                "abc2@example.com",
                passwordEncoder.encode("1234"),
                "123-4567-9101",
                UserRole.ROLE_USER);
        userService.join(sud);

        //when
        User findUser = userRepository.findByEmail("abc2@example.com").get();

        //then
        assertEquals(findUser.getStatus(),UserStatus.USER_STATUS_ACTIVE);
    }

    @Test
    void 로그인() {
        //given
        UserSignInDto sid = new UserSignInDto("abc2@example.com","1234");
        userService.logIn(sid);
        //when
        SecurityContextHolder.getContext().setAuthentication(td.getAuthentication(td.resolveToken(mockRequest)));
        String email = td.currentUser().getEmail();
        //then
        assertEquals(email,"abc2@example.com");
    }
    @Test
    void 비회원_로그인(){
        //given
        UserSignInDto sid = new UserSignInDto("abcasdf@naver.com","4567");

        //when, then
        assertThrows(NoSuchElementException.class,() -> userService.logIn(sid));

    }
    @Test
    void 비밀번호_불일치(){
        //given
        UserSignInDto sid = new UserSignInDto("abc2@example.com","12345");

        //when, then
        assertThrows(NoSuchElementException.class,() -> userService.logIn(sid));
    }
    @Test
    void 탈퇴한_회원_로그인(){
        //given
        Optional<User> ckUser = userRepository.findByEmail("abc2@example.com");
        td.createToken(String.valueOf(ckUser.get().getRole()), String.valueOf(ckUser.get().getUserId()));
        SecurityContextHolder.getContext().setAuthentication(td.getAuthentication(td.resolveToken(mockRequest)));
        userService.deactive("1234");
        SecurityContextHolder.clearContext();
        UserSignInDto sid = new UserSignInDto("abc2@example.com","1234");

        //when, then
        assertThrows(NoSuchElementException.class,() -> userService.logIn(sid));
    }

    @Test
    void 정보_업데이트() {
        //given
        Optional<User> ckUser = userRepository.findByEmail("abc2@example.com");
        td.createToken(String.valueOf(ckUser.get().getRole()), String.valueOf(ckUser.get().getUserId()));
        SecurityContextHolder.getContext().setAuthentication(td.getAuthentication(td.resolveToken(mockRequest)));

        //when
        userService.updateInfo(new UserInfoDto(userRepository.findByTokenId(td.currentUserId())));
        User changeUser = userRepository.findByEmail("abc2@example.com").get();

        //then
        assertThat(changeUser.getName().equals("김채림"));
        assertThat(changeUser.getEmail().equals("abc2@example.com"));
        assertThat(changeUser.getPhoneNumber().equals("010-5607-7854"));
    }
}