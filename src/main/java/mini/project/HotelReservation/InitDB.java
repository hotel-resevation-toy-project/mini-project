package mini.project.HotelReservation;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.enumerate.RoomType;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;
    @PostConstruct
    public void init() {
        initService.hotelA();
        initService.hotelB();
        initService.hotelC();
    }

    @Component
    @Transactional
    public static class InitService {
        private final EntityManager em;
        private final PasswordEncoder pe;

        @Autowired
        public InitService(EntityManager em, PasswordEncoder pe) {
            this.em = em;
            this.pe = pe;
        }

        public void hotelA(){
            User user = new User("오진석",
                    "ojs258@naver.com",
                    pe.encode("1234"),//1234
                    "010-4620-9138",
                    UserStatus.USER_STATUS_ACTIVE,
                    UserRole.ROLE_USER);

            User host = new User("Carder_Garden",
                    "Carder_Garden@reservation.com",
                    pe.encode("1234"),//1234
                    "031-239-5572",
                    UserStatus.USER_STATUS_ACTIVE,
                    UserRole.ROLE_HOST);

            Room roomA = new Room(RoomType.ROOM_TYPE_A_SINGLE, 100000, 10);
            Room roomB = new Room(RoomType.ROOM_TYPE_B_TWIN, 200000, 20);

            Hotel hotel = new Hotel("수원시 권선구 권선동",
                    "Carder_Garden",
                    "031-239-5572",
                    DiscountPolicy.POLICY_PEAK,
                    LocalTime.of(15, 0, 0),
                    LocalTime.of(12, 0, 0),
                    LocalDate.of(0, 7, 15),
                    LocalDate.of(0, 9, 15));
            hotel.foreignUser(host);
            em.persist(hotel);
            em.persist(user);
            roomA.foreignHotel(hotel);
            roomB.foreignHotel(hotel);
            em.persist(roomA);
            em.persist(roomB);
        }

        public void hotelB(){
            User user = new User("김채림",
                    "notcherry@naver.com",
                    pe.encode("2580"),//2580
                    "010-4433-4815",
                    UserStatus.USER_STATUS_ACTIVE,
                    UserRole.ROLE_USER);

            User host = new User("Hilton_Budapest",
                    "Hilton_Budapest@reservation.com",
                    pe.encode("2580"),//2580
                    "+36)1-889-6600",
                    UserStatus.USER_STATUS_ACTIVE,
                    UserRole.ROLE_HOST);

            Room roomA = new Room(RoomType.ROOM_TYPE_A_SINGLE, 100000, 10);
            Room roomB = new Room(RoomType.ROOM_TYPE_B_TWIN, 200000, 15);
            Room roomC = new Room(RoomType.ROOM_TYPE_C_QUEEN, 300000, 20);

            Hotel hotel = new Hotel("1014 Budapest, Hess Andras ter 1-3",
                    "Hilton_Budapest",
                    "+36)1-889-6600",
                    DiscountPolicy.POLICY_DAYS,
                    LocalTime.of(15, 0, 0),
                    LocalTime.of(11, 0, 0),
                    LocalDate.of(0, 7, 15),
                    LocalDate.of(0, 9, 15));
            hotel.foreignUser(host);
            em.persist(hotel);
            em.persist(user);
            roomA.foreignHotel(hotel);
            roomB.foreignHotel(hotel);
            roomC.foreignHotel(hotel);
            em.persist(roomA);
            em.persist(roomB);
            em.persist(roomC);

        }

        public void hotelC(){
            Room roomA = new Room(RoomType.ROOM_TYPE_A_SINGLE, 100000, 10);
            Room roomB = new Room(RoomType.ROOM_TYPE_B_TWIN, 200000, 20);
            Room roomC = new Room(RoomType.ROOM_TYPE_C_QUEEN, 300000, 15);
            Room roomD = new Room(RoomType.ROOM_TYPE_D_KING, 400000, 10);

            Hotel hotel = new Hotel("인천시 중구 영종도",
                    "CityOf_Paradise",
                    "032-1833-8855",
                    DiscountPolicy.POLICY_ALL,
                    LocalTime.of(15, 0, 0),
                    LocalTime.of(11, 0, 0),
                    LocalDate.of(0, 11, 15),
                    LocalDate.of(0, 1, 15));
            em.persist(hotel);
            roomA.foreignHotel(hotel);
            roomB.foreignHotel(hotel);
            roomC.foreignHotel(hotel);
            roomD.foreignHotel(hotel);
            em.persist(roomA);
            em.persist(roomB);
            em.persist(roomC);
            em.persist(roomD);
        }
    }
}
