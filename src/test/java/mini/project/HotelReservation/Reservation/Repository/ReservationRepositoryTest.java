package mini.project.HotelReservation.Reservation.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @AfterEach
    void reset(){
        userRepository.deleteAll();
        reservationRepository.deleteAll();
    }
    @BeforeEach
    void init(){
        userRepository.save(new User("오진석",
                "abc@example.com",
                "1234",
                "010-1234-5678",
                UserStatus.USER_STATUS_ACTIVE,
                UserRole.ROLE_USER));
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