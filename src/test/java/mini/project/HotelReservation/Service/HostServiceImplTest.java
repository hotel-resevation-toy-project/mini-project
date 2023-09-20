package mini.project.HotelReservation.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.Host.Service.HostService;
import mini.project.HotelReservation.Host.Service.HostServiceImpl;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HostServiceImplTest {
    private final HostService hostService;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final TokenDecoder td;

    @Mocked
    HttpServletRequest mockRequest;
    @Mocked
    HttpServletResponse mockResponse;

    // 자동 주입
    @Autowired
    public HostServiceImplTest(HostService hostService, HotelRepository hotelRepository, RoomRepository roomRepository, ReservationRepository reservationRepository, UserRepository userRepository, TokenDecoder td) {
        this.hostService = hostService;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.td = td;
    }

    // 초기화
    @BeforeEach
    public void init(){
        hotelRepository.deleteAll();
        roomRepository.deleteAll();
        roomRepository.deleteAll();
        td.init();
        mockRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    @Test
    @DisplayName("호스트_정책_변경")
    void change_Policy() {
        // 임의 유저
        User user = new User("Serah","sexy123@play.data",
                "123", "123-4567-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_HOST);
        // 임의 호텔 , 정책 = POLICY_DAYS
        Hotel hotel = new Hotel("신대방", "그부호",
                "010-1234-1234", DiscountPolicy.POLICY_DAYS,
                LocalTime.NOON, LocalTime.MIDNIGHT,
                LocalDate.now(), LocalDate.now().plusMonths(2));
        // 호스트에 호텔 주입
        user.foreignHotel(hotel);

        hotelRepository.save(hotel);
        userRepository.save(user);

        // 저장된 유저에서 연결된 호텔 받아오기
        Hotel checkHotel = userRepository.findById(1L).get().getHotel();
        checkHotel.changePolicy(DiscountPolicy.POLICY_ALL);

        //when, then
        assertEquals(checkHotel.getDiscountPolicy(), DiscountPolicy.POLICY_ALL);
    }

    @Test
    @DisplayName("회원 상태 확인 테스트")
    void checkStatus() {
    }

    @Test
    void logIn() {
    }

    @Test
    void updateInfo() {

    }

    @Test
    void deactive() {

    }

    @Test
    void reservationList() {
    }

    @Test
    void loadUserByUserId() {
    }
}