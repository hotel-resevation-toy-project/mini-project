package mini.project.HotelReservation.User.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.AuditTime;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.User.Data.Dto.UserInfoDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import org.apache.catalina.users.SparseUserDatabase;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends AuditTime {

    @Id @GeneratedValue
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Reservation> reservations = new ArrayList<>();

    public User(UserSignUpDto userSignUpDto){
        this.name = userSignUpDto.getName();
        this.email = userSignUpDto.getEmail();
        this.password = userSignUpDto.getPassword();
        this.phoneNumber = userSignUpDto.getPhoneNumber();
        this.status = UserStatus.USER_STATUS_ACTIVE;
        this.role = userSignUpDto.getRole();
    }

    //유저가 호텔인 경우, 호텔 아이디를 조회해서 호텔 가져오기
    public void foreignHotel(Hotel foreignHotel){
            hotel = foreignHotel;
    }

    public void updateInfo(UserInfoDto userInfoDto){
        name = userInfoDto.getName();
        email = userInfoDto.getEmail();
        phoneNumber = userInfoDto.getPhoneNumber();
    }

    public void changeStatus(){
        this.status = UserStatus.USER_STATUS_ACTIVE;
    }

    public void deactive(){
        status = UserStatus.USER_STATUS_DEACTIVE;
    }
}
