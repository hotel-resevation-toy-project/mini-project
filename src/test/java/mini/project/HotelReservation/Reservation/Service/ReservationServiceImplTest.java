package mini.project.HotelReservation.Reservation.Service;

import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.Host.Service.HostService;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.User.Service.UserService;
import mini.project.HotelReservation.User.Service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    public ReservationServiceImplTest(UserRepository userRepository, ReservationRepository reservationRepository, HotelRepository hotelRepository, RoomRepository roomRepository, UserService userService, ReservationService reservationService, HostService hostService, TokenDecoder td) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.reservationService = reservationService;
        this.hostService = hostService;
        this.td = td;
    }

    @Test
    void 예약시_호텔() {
    }

    @Test
    void findByRoomList() {
    }

    @Test
    void discountPrice() {
    }

    @Test
    void reserve() {
    }

    @Test
    void createReserveNumber() {
    }

    @Test
    void reserveInfo() {
    }

    @Test
    void reserveDelete() {
    }
}