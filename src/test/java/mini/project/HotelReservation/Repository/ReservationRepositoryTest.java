package mini.project.HotelReservation.Entity;

import mini.project.HotelReservation.Data.Entity.Hotel;
import mini.project.HotelReservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Data.Entity.Room;
import mini.project.HotelReservation.Data.Entity.User;
import mini.project.HotelReservation.Data.Enum.DiscountPolicy;
import mini.project.HotelReservation.Data.Enum.RoomType;
import mini.project.HotelReservation.Data.Enum.UserRole;
import mini.project.HotelReservation.Data.Enum.UserStatus;
import mini.project.HotelReservation.Repository.HotelRepository;
import mini.project.HotelReservation.Repository.ReservationRepository;
import mini.project.HotelReservation.Repository.RoomRepository;
import mini.project.HotelReservation.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ReservationTest {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationTest(ReservationRepository reservationRepository, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Test
    void 예약_번호로_예약_조회() {
        Reservation reservationA = new Reservation("A-1",
                123456,
                RoomType.ROOM_TYPE_A_SINGLE,
                "A",
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        reservationRepository.save(reservationA);

        Reservation result = reservationRepository.findByReserveNumber("A-1");

        assertEquals(result,reservationA);
    }

    @Test
    void 회원별_예약_전체_조회() {
        //given
        User user = new User("오진석",
                "ojs258@asdf",
                "1234",
                "010-1234-5678",
                UserStatus.USER_STATUS_ACTIVE,
                UserRole.ROLE_USER);
        userRepository.save(user);

        Hotel hotel = new Hotel("서울",
                "Hotel_A",
                "02-356-5598",
                DiscountPolicy.POLICY_PEAK,
                LocalTime.of(12, 0, 0),
                LocalTime.of(9, 0, 0),
                LocalDate.of(LocalDate.now().getYear(), 7, 15),
                LocalDate.of(LocalDate.now().getYear(), 9, 15));
        hotelRepository.save(hotel);

        Room roomA = new Room(RoomType.ROOM_TYPE_A_SINGLE, 100000, 5);
        roomA.foreignHotel(hotel);
        Room roomB = new Room(RoomType.ROOM_TYPE_B_TWIN, 200000, 5);
        roomB.foreignHotel(hotel);
        roomRepository.save(roomA);
        roomRepository.save(roomB);

        Reservation reservationA = new Reservation("A-1",
                1234568,
                RoomType.ROOM_TYPE_A_SINGLE,
                "A",
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        reservationA.foreignUser(user);
        reservationA.foreignRoom(roomA);
        reservationRepository.save(reservationA);

        Reservation reservationB = new Reservation("A-2",
                1564878,
                RoomType.ROOM_TYPE_B_TWIN,
                "A",
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        reservationB.foreignUser(user);
        reservationB.foreignRoom(roomB);
        reservationRepository.save(reservationB);

        //when
        List<Reservation> result = reservationRepository.findAllByUser_UserId(1L);

        //then
        assertEquals(result.size(),2);
    }

    @Test
    void 현재_예약중_호텔_정보_조회() {
        Hotel hotel = new Hotel("서울",
                "Hotel_A",
                "02-356-5598",
                DiscountPolicy.POLICY_PEAK,
                LocalTime.of(12, 0, 0),
                LocalTime.of(9, 0, 0),
                LocalDate.of(LocalDate.now().getYear(), 7, 15),
                LocalDate.of(LocalDate.now().getYear(), 9, 15));
        hotelRepository.save(hotel);

        Hotel hotelA = reservationRepository.findByHotelName("Hotel_A");

        assertEquals(hotelA.getHotelName(),"Hotel_A");
        assertEquals(hotelA.getHotelPhoneNumber(),"02-356-5598");
    }

    @Test
    void 예약_번호로_예약_취소() {
        //given
        User user = new User("오진석",
                "ojs258@asdf",
                "1234",
                "010-1234-5678",
                UserStatus.USER_STATUS_ACTIVE,
                UserRole.ROLE_USER);
        userRepository.save(user);

        Hotel hotel = new Hotel("서울",
                "Hotel_A",
                "02-356-5598",
                DiscountPolicy.POLICY_PEAK,
                LocalTime.of(12, 0, 0),
                LocalTime.of(9, 0, 0),
                LocalDate.of(LocalDate.now().getYear(), 7, 15),
                LocalDate.of(LocalDate.now().getYear(), 9, 15));
        hotelRepository.save(hotel);

        Room roomA = new Room(RoomType.ROOM_TYPE_A_SINGLE, 100000, 5);
        roomA.foreignHotel(hotel);
        Room roomB = new Room(RoomType.ROOM_TYPE_B_TWIN, 200000, 5);
        roomB.foreignHotel(hotel);
        roomRepository.save(roomA);
        roomRepository.save(roomB);

        Reservation reservationA = new Reservation("A-1",
                1234568,
                RoomType.ROOM_TYPE_A_SINGLE,
                "A",
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        reservationA.foreignUser(user);
        reservationA.foreignRoom(roomA);
        reservationRepository.save(reservationA);

        Reservation reservationB = new Reservation("A-2",
                1564878,
                RoomType.ROOM_TYPE_B_TWIN,
                "A",
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        reservationB.foreignUser(user);
        reservationB.foreignRoom(roomB);
        reservationRepository.save(reservationB);

        //when
        reservationRepository.deleteByReserveNumber("A-1");
        List<Reservation> result = reservationRepository.findAllByUser_UserId(1L);

        assertEquals(result.size(),1);
    }
}