package mini.project.HotelReservation.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.enumerate.RoomType;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import mini.project.HotelReservation.User.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ReservationRepositoryTest {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationRepositoryTest(ReservationRepository reservationRepository, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @BeforeEach
    public void init(){
        reservationRepository.deleteAll();
        userRepository.deleteAll();
        hotelRepository.deleteAll();
    }

    @Test
    void 예약_번호로_예약_조회() {
        Reservation reservationA = new Reservation("A-3",
                123456,
                RoomType.ROOM_TYPE_A_SINGLE,
                "A",
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        Reservation save = reservationRepository.save(reservationA);

        Reservation result = reservationRepository.findByReserveNumber("A-3");

        assertEquals(result,save);
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
        User saveUser = userRepository.save(user);

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

        Reservation reservationA = new Reservation("A-4",
                1234568,
                RoomType.ROOM_TYPE_A_SINGLE,
                "A",
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        reservationA.foreignUser(saveUser);
        reservationA.foreignRoom(roomA);
        Reservation re1 = reservationRepository.save(reservationA);

        Reservation reservationB = new Reservation("A-5",
                1564878,
                RoomType.ROOM_TYPE_B_TWIN,
                "A",
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        reservationB.foreignUser(saveUser);
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
        List<Reservation> result = reservationRepository.findAll();

        assertEquals(result.size(),1);
    }

    @Test
    void 호텔별_전체_예약_조회(){
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
        roomRepository.save(roomA);

        Reservation reservationA = new Reservation("A-1",
                1234568,
                RoomType.ROOM_TYPE_A_SINGLE,
                hotel.getHotelName(),
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        reservationA.foreignUser(user);
        reservationA.foreignRoom(roomA);
        reservationRepository.save(reservationA);

        Reservation reservationB = new Reservation("A-2",
                1564878,
                RoomType.ROOM_TYPE_A_SINGLE,
                hotel.getHotelName(),
                "010-1234-5678",
                "오진석",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        reservationB.foreignUser(user);
        reservationB.foreignRoom(roomA);
        reservationRepository.save(reservationB);

        //when
        List<Reservation> hotelA = reservationRepository.findReservationListByHotelNameAndRoomType("Hotel_A", RoomType.ROOM_TYPE_A_SINGLE);

        assertEquals(hotelA.size(),2);
    }
}