package mini.project.HotelReservation.Host.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.Host.Service.HostService;
import mini.project.HotelReservation.Host.Service.HostServiceImpl;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.enumerate.RoomType;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import mockit.Mocked;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    @DisplayName("호스트_정책_변경")
    void change_Policy() {
        // 임의 유저
        User user = new User("그부호","sexy123@play.data",
                "123", "123-4567-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_HOST);
        // 임의 호텔 , 정책 = POLICY_DAYS
        Hotel hotel = new Hotel("신대방", "그부호",
                "010-1234-1234", DiscountPolicy.POLICY_DAYS,
                LocalTime.NOON, LocalTime.MIDNIGHT,
                LocalDate.now(), LocalDate.now().plusMonths(2));

        // 호스트에 호텔 주입
        user.foreignHotel(hotel);
        userRepository.save(user);

        // 저장된 유저에서 연결된 호텔 받아오기
        User checkUser = userRepository.findById(1L).get();
        Hotel checkHotel = checkUser.getHotel();
        checkHotel.changePolicy(DiscountPolicy.POLICY_ALL);

        //when, then
        assertEquals(checkHotel.getDiscountPolicy(), DiscountPolicy.POLICY_ALL);
    }

    @Test
    @Transactional
    @DisplayName("방가격_변경")
    void checkStatus() {
        // 임의 유저
        User user = new User("Serah","sexy123@play.data",
                "123", "123-4567-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_HOST);
        // 임의 호텔
        Hotel hotel = new Hotel("신대방", "그부호",
                "010-1234-1234", DiscountPolicy.POLICY_DAYS,
                LocalTime.NOON, LocalTime.MIDNIGHT,
                LocalDate.now(), LocalDate.now().plusMonths(2));
        // 임의 객실 -> 가격변경할거임
        Room room = new Room(RoomType.ROOM_TYPE_A_SINGLE, 100000,20);
//        hotel.getRooms().add(room);
        room.foreignHotel(hotel);
        // 호스트에 호텔 주입
        user.foreignHotel(hotel);
        userRepository.save(user);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(userRepository.findById(1L).get().getHotel().getRooms().get(0).getRoomPrice());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@");
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