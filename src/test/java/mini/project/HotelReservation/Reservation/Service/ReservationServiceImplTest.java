package mini.project.HotelReservation.Reservation.Service;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.Host.Service.HostService;
import mini.project.HotelReservation.Reservation.Data.Dto.*;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.User.Service.UserService;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ReservationServiceImplTest {
    UserRepository userRepository;
    ReservationRepository reservationRepository;
    HotelRepository hotelRepository;
    RoomRepository roomRepository;
    UserService userService;
    ReservationService reservationService;
    HostService hostService;
    TokenDecoder td;
    EntityManager em;
    @Mocked
    HttpServletRequest mockRequest;

    @Autowired
    public ReservationServiceImplTest(UserRepository userRepository, ReservationRepository reservationRepository, HotelRepository hotelRepository, RoomRepository roomRepository, UserService userService, ReservationService reservationService, HostService hostService, TokenDecoder td, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.reservationService = reservationService;
        this.hostService = hostService;
        this.td = td;
        this.em = entityManager;
    }



//    @AfterEach
//    void reset(){
//        hotelRepository.deleteAll();
//        roomRepository.deleteAll();
//        userRepository.deleteAll();
//        reservationRepository.deleteAll();
//    }

    @BeforeEach
    void init(){
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
        User userA = userRepository.save(user);
        // 호텔 생성
        // A
        Hotel hotel = new Hotel("성북구",
                "Hotel_A",
                "02-123-4567",
                DiscountPolicy.POLICY_PEAK,
                LocalTime.of(13, 0, 0),
                LocalTime.of(10, 0, 0),
                LocalDate.of(2023,7,15),
                LocalDate.of(2023,9,15));
        hotel.foreignUser(host);
        Hotel saveHotel = hotelRepository.save(hotel);
        // B
        Hotel hotelB = new Hotel("신대방",
                "Hotel_B",
                "02-123-4567",
                DiscountPolicy.POLICY_DAYS,
                LocalTime.of(13, 0, 0),
                LocalTime.of(18, 0, 0),
                LocalDate.of(2023,7,15),
                LocalDate.of(2023,9,15));

        Hotel hotelC = new Hotel("신대방",
                "Hotel_C",
                "02-123-4567",
                DiscountPolicy.POLICY_ALL,
                LocalTime.of(13, 0, 0),
                LocalTime.of(18, 0, 0),
                LocalDate.of(2023,7,15),
                LocalDate.of(2023,9,15));

        // 귀찮아서 얘는 호스트 없음
        Hotel saveHotelB = hotelRepository.save(hotelB);
        hotelRepository.save(hotelC);
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
                LocalDateTime.of(LocalDate.of(2023, 9, 10),LocalTime.of(13, 0, 0)),
                LocalDateTime.of(LocalDate.of(2023, 9, 20),LocalTime.of(10, 0, 0)));
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
        td.createToken(String.valueOf(userA.getRole()), "2");
        SecurityContextHolder.getContext().setAuthentication(td.getAuthentication(td.resolveToken(mockRequest)));
    }

    @Test
    void 예약시_호텔_목록() {
        List<HotelDto> result = reservationService.findByHotelList();
        assertEquals(result.size(),2);
    }

    @Test
    void 예약시_호텔별_방_목록() {
        List<RoomDto> result = reservationService.findByRoomList("Hotel_A");
        assertEquals(result.size(),2);
    }

    @Test
    void 금액_할인() {
        /*
        1. Peak -> Hotel_A (성수기 -> 비 성수기로 넘어가는 로직)
        2. Days -> Hotel_B
        3. ALL -> Hotel_C
        4. Peak정책 호텔 A를 가지고 정률, 정액
        */
        DiscountPriceDto peakDiscountPriceDto = reservationService.discountPrice(
                new ReservationRequestDto("Hotel_A",
                        LocalDate.of(2023,9,10),
                        LocalDate.of(2023,9,20),
                        RoomType.ROOM_TYPE_A_SINGLE,
                        100000));

        DiscountPriceDto daysDiscountPriceDto = reservationService.discountPrice(
                new ReservationRequestDto("Hotel_B",
                        LocalDate.of(2023,9,10),
                        LocalDate.of(2023,9,20),
                        RoomType.ROOM_TYPE_A_SINGLE,
                        100000));

        DiscountPriceDto allDiscountPriceDto = reservationService.discountPrice(
                new ReservationRequestDto("Hotel_C",
                        LocalDate.of(2023,9,10),
                        LocalDate.of(2023,9,20),
                        RoomType.ROOM_TYPE_A_SINGLE,
                        100000));

//        Peak
        assertEquals(String.valueOf(peakDiscountPriceDto.getDiscountPolicy()), String.valueOf(DiscountPolicy.POLICY_PEAK));
        assertEquals(peakDiscountPriceDto.getTotalPrice(),10*100000);
        assertEquals(peakDiscountPriceDto.getDiscountPrice(),150000);
        assertEquals(peakDiscountPriceDto.getPay(),850000);


//        Days
        assertEquals(String.valueOf(daysDiscountPriceDto.getDiscountPolicy()), String.valueOf(DiscountPolicy.POLICY_DAYS));
        assertEquals(daysDiscountPriceDto.getTotalPrice(),10*100000);
        assertEquals(daysDiscountPriceDto.getPay(),857375);
        assertEquals(daysDiscountPriceDto.getDiscountPrice(),142625);

//        All
        assertEquals(String.valueOf(allDiscountPriceDto.getDiscountPolicy()), "성수기, 연박 두 할인 중 더 큰 할인이 적용 되었습니다.");
        assertEquals(allDiscountPriceDto.getTotalPrice(),10*100000);
        assertEquals(allDiscountPriceDto.getPay(),850000);
        assertEquals(allDiscountPriceDto.getDiscountPrice(),150000);
    }

    @Test
    void 예약() {
        ReservationResponseDto reserve = reservationService.reserve(
                            new ReservationRequestDto(
                            "Hotel_A",
                            LocalDate.of(2023, 9, 10),
                            LocalDate.of(2023, 9, 20),
                            RoomType.ROOM_TYPE_A_SINGLE,
                            100000),
                    reservationService.discountPrice(
                            new ReservationRequestDto(
                            "Hotel_A",
                            LocalDate.of(2023, 9, 10),
                            LocalDate.of(2023, 9, 20),
                            RoomType.ROOM_TYPE_A_SINGLE,
                            100000)));
        User user = td.currentUser();
        Hotel hotel = hotelRepository.findByHotelName("Hotel_A");
        assertEquals(reserve.getUserName(),user.getName());
        assertEquals(reserve.getPhoneNumber(),user.getPhoneNumber());
        assertEquals(reserve.getHotelName(),"Hotel_A");
        assertEquals(reserve.getRoomType(),RoomType.ROOM_TYPE_A_SINGLE);
        assertEquals(reserve.getCheckInDate(), LocalDateTime.of(LocalDate.of(2023, 9, 10),hotel.getCheckInTime()));
        assertEquals(reserve.getCheckOutDate(),LocalDateTime.of(LocalDate.of(2023, 9, 20),hotel.getCheckOutTime()));
        assertEquals(reserve.getReservationNumber(),"AA2-230910");
        assertEquals(reserve.getReservePrice(),850000);
    }

    @Test
    void 예약_번호_만들기() {
        ReservationRequestDto hotelA = new ReservationRequestDto(
                "Hotel_A",
                LocalDate.of(2023, 9, 10),
                LocalDate.of(2023, 9, 20),
                RoomType.ROOM_TYPE_A_SINGLE,
                100000);
        String reserveNumber = reservationService.createReserveNumber(
                hotelRepository.findByHotelName(hotelA.getHotelName()),
                hotelA);
        assertEquals(reserveNumber,"AA2-230910");
    }

    @Test
    void 예약_정보() {
        ReservationResponseDto reserve = reservationService.reserveInfo("AA1-230523");

        assertEquals(reserve.getUserName(), "Serah");
        assertEquals(reserve.getPhoneNumber(), "010-2222-3333");
        assertEquals(reserve.getHotelName(), "Hotel_A");
        assertEquals(reserve.getRoomType(), RoomType.ROOM_TYPE_A_SINGLE);
        assertEquals(reserve.getCheckInDate(), LocalDateTime.of(LocalDate.of(2023, 9, 10),LocalTime.of(13, 0, 0)));
        assertEquals(reserve.getCheckOutDate(), LocalDateTime.of(LocalDate.of(2023, 9, 20),LocalTime.of(10, 0, 0)));
        assertEquals(reserve.getReservationNumber(), "AA1-230523");
        assertEquals(reserve.getReservePrice(), 2100000);
    }

    @Test
    @Rollback(value = false)
    void 예약_삭제() {
        reservationService.reserveDelete("AA1-230523");
        List<Reservation> all = reservationRepository.findAll();
        assertEquals(all.size(),2);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@222");
        for (Reservation reservation : all) {
            System.out.println("reservation = " + reservation.getReserveNumber());
        }
    }
}