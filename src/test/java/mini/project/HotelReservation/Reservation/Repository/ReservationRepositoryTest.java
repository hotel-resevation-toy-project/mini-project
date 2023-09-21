package mini.project.HotelReservation.Reservation.Repository;

import jakarta.servlet.http.HttpServletRequest;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
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
    @Autowired
    TokenDecoder td;

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
    }

    @Test
    void 예약_번호로_예약_찾기() {
        String reservationNum = reservationRepository.findAll().get(0).getReserveNumber();
        assertEquals(reservationRepository.findByReserveNumber(reservationNum).getReserveId(), reservationRepository.findAll().get(0).getReserveId());
    }

    @Test
    void 유저_아이디로_예약_목록_조회() {
        // 로그인된 User를 Security Context에 저장
            // 유저 오진석을 저장
        User loginUser = null;
        for(User ckuser : userRepository.findAll()){
            if(ckuser.getName().equals("오진석")){
                loginUser = ckuser;
            }
        }
        td.init();
        mockRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        td.createToken(String.valueOf(loginUser.getRole()), String.valueOf(loginUser.getUserId()));
        SecurityContextHolder.getContext().setAuthentication(td.getAuthentication(td.resolveToken(mockRequest)));
        User currentUser = td.currentUser();

        List<Reservation> reserveList = reservationRepository.findAllByUser_UserId(currentUser.getUserId());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        for(Reservation reseve : reserveList){
            System.out.println("예약번호 : "+reseve.getReserveNumber()+"\t 호텔주소 : "+reseve.getHotel().getAddress());
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        assertEquals(2, reserveList.size());
    }

    @Test
    void 호텔_아이디로_예약_목록_조회() {
        // 로그인된 host User를 Security Context에 저장
            // 유저 호스트를 저장
        User hostUser = null;
        for(User ckuser : userRepository.findAll()){
            if(ckuser.getHotel() != null){
                hostUser = ckuser;
            }
        }
        td.init();
        mockRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        td.createToken(String.valueOf(hostUser.getRole()), String.valueOf(hostUser.getUserId()));
        SecurityContextHolder.getContext().setAuthentication(td.getAuthentication(td.resolveToken(mockRequest)));
            // 현재 로그인된 호스트 유저에서 hotelId 받기
        User host = td.currentUser();
        Long hotelId = host.getHotel().getHotelId();

        List<Reservation> reseverList = reservationRepository.findAllByHotel_HotelId(hotelId);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        for(Reservation reseve : reseverList){
            System.out.println("예약번호 : "+reseve.getReserveNumber()+"\t 호텔주소 : "+reseve.getHotel().getHotelName());
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        assertEquals(2, reseverList.size());
    }

    @Test
    void 예약시_선택한_호텔_정보와_룸_타입으로_예약_개수_구하기() {
        String hotelName = "Hotel_A";
        RoomType rT = RoomType.ROOM_TYPE_A_SINGLE;
        Long count = reservationRepository.findCountByHotelNameAndRoom(hotelName, rT);
        assertEquals(1, count);
    }

    @Test
    void 예약_번호로_예약_삭제() {
        Reservation reserve = reservationRepository.findAll().get(0);
        Long reservationId = reserve.getReserveId();
        String reservationNum = reserve.getReserveNumber();

        reservationRepository.deleteByReserveNumber(reservationNum);
        assertEquals(false, reservationRepository.findById(reservationId).isPresent());
    }
}
