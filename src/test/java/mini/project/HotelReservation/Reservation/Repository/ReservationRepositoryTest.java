package mini.project.HotelReservation.Reservation.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.enumerate.RoomType;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;

    @AfterEach
    void reset(){
        hotelRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
        reservationRepository.deleteAll();
    }
    @BeforeEach
    void init(){
        User host = new User("Hotel_A",
                "abc@example.com",
                "1234",
                "010-1234-5678",
                UserStatus.USER_STATUS_ACTIVE,
                UserRole.ROLE_HOST);

        Hotel hotel = new Hotel("성북구",
                "Hotel_A",
                "02-123-4567",
                DiscountPolicy.POLICY_PEAK,
                LocalTime.of(13, 0, 0),
                LocalTime.of(10, 0, 0),
                LocalDate.now(),
                LocalDate.now().plusMonths(2));
        host.foreignHotel(hotel);

        Room roomA = new Room(RoomType.ROOM_TYPE_A_SINGLE, 100000, 10);
        roomA.foreignHotel(hotel);

        Room roomB = new Room(RoomType.ROOM_TYPE_B_TWIN, 200000, 20);
        roomB.foreignHotel(hotel);

        Room roomC = new Room(RoomType.ROOM_TYPE_C_QUEEN, 300000, 20);
        roomC.foreignHotel(hotel);

        userRepository.save(new User("오진석",
                "abc@example.com",
                "1234",
                "010-1234-5678",
                UserStatus.USER_STATUS_ACTIVE,
                UserRole.ROLE_USER));
        userRepository.save(host);


    }
    @Test
    void 예약_번호로_예약_찾기() {
    }

    @Test
    void 유저_아이디로_예약_목록_조회() {
    }

    @Test
    void 호텔_아이디로_예약_목록_조회() {
    }

    @Test
    void 예약시_선택한_호텔_정보와_룸_타입으로_예약_개수_구하기() {
    }

    @Test
    void 예약_번호로_예약_삭제() {
    }
}