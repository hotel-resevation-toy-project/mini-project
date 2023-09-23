package mini.project.HotelReservation.Host.Repository;

import jakarta.servlet.http.HttpServletRequest;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.enumerate.RoomType;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import mockit.Mocked;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class HotelRepositoryTest {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final TokenDecoder td;

    @Autowired
    public HotelRepositoryTest(UserRepository userRepository, ReservationRepository reservationRepository, HotelRepository hotelRepository, RoomRepository roomRepository, TokenDecoder td) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.td = td;
    }

    @Mocked
    HttpServletRequest mockRequest;

    @AfterEach
    void reset(){
        hotelRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
        reservationRepository.deleteAll();
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
                "abc@example.com",
                "1234",
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

        User ckUser = userRepository.findById(userRepository.findAll().get(0).getUserId()).get();
        td.createToken(String.valueOf(ckUser.getRole()), String.valueOf(ckUser.getUserId()), String.valueOf(ckUser.getHotel().getHotelId()));
        SecurityContextHolder.getContext().setAuthentication(td.getAuthentication(td.resolveToken(mockRequest)));
    }
    @Test
    void 호텔_이름으로_호텔_찾기(){
        assertEquals(hotelRepository.findByHotelName("Hotel_A"),
                hotelRepository.findByHotelId(hotelRepository.findAll().get(0).getHotelId()));
    }
    @Test
    void 호텔_아이디로_호텔_찾기(){
        assertEquals(hotelRepository.findByHotelId(hotelRepository.findAll().get(0).getHotelId()),
                hotelRepository.findByHotelId(hotelRepository.findAll().get(0).getHotelId()));
    }
}
