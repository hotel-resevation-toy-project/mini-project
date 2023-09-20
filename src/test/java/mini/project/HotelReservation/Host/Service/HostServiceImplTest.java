package mini.project.HotelReservation.Host.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Dto.HotelReservationDto;
import mini.project.HotelReservation.Host.Data.Dto.PriceDto;
import mini.project.HotelReservation.Host.Data.Dto.RoomStockDto;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
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
import org.junit.jupiter.api.DisplayName;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
        td.init();
        mockRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // 임의 유저
        User user = new User("Serah","sexy123@play.data",
                "123", "123-4567-9101", UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_HOST);
        // 임의 호텔    -> 정책변경할거임
        Hotel hotel = new Hotel("신대방", "그부호",
                "010-1234-1234", DiscountPolicy.POLICY_DAYS,
                LocalTime.NOON, LocalTime.MIDNIGHT,
                LocalDate.now(), LocalDate.now().plusMonths(2));
        // 임의 객실    -> 가격변경할거임
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
    @DisplayName("호스트_정책_변경")
    void change_Policy() {
        System.out.println("$$$$$$$$$");
        System.out.println(DiscountPolicy.valueOf("POLICY"));
        System.out.println("$$$$$$$$$");
        // 저장된 유저에서 연결된 호텔 받아오기
        hostService.changePolicy(DiscountPolicy.POLICY_ALL);
        assertEquals(td.currentUser().getHotel().getDiscountPolicy(), DiscountPolicy.POLICY_ALL);
    }
    @Test
    @DisplayName("방가격_변경")
    void modifyRoomPrice() {
        PriceDto priceDto = new PriceDto(RoomType.ROOM_TYPE_A_SINGLE, 500);
        hostService.modifyRoomPrice(priceDto);
        assertEquals(roomRepository.findById(roomRepository.findAll().get(0).getRoomId()).get().getRoomPrice(), 500);
    }
    @Test
    @DisplayName("방개수_변경")
    void modifyRoomStock() {
        RoomStockDto stockDto = new RoomStockDto(RoomType.ROOM_TYPE_A_SINGLE, 50);
        hostService.modifyRoomStock(stockDto);
        assertEquals(roomRepository.findById(
                userRepository.findAll().get(0).getUserId()).get().getRoomStock(), 50);
    }
    @Test
    @DisplayName("호텔측_예약리스트_보기")
    void reservationList() {
        List<HotelReservationDto> reservations = hostService.reservationList();
        assertEquals(reservations.size(),
                reservationRepository.findAllByHotel_HotelId(
                        userRepository.findById(userRepository.findAll().get(0).getUserId()).get().getHotel().getHotelId()
                ).size());
    }
}

//               System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        for(HotelReservationResponseDto reservation : reservations){
//                System.out.println("UserName : " + reservation.getUserName()+
//                "\t/ PhoneNumber : " + reservation.getUserPhoneNumber());
//                }
//                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");