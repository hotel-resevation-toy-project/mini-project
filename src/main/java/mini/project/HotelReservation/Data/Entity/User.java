package mini.project.HotelReservation.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.Data.AuditTime;
import mini.project.HotelReservation.Data.Enum.UserRole;
import mini.project.HotelReservation.Data.Enum.UserStatus;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends AuditTime {

    @Id @GeneratedValue
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "user")
    List<Reservation> reservations;

    public User(String name, String email, String password,
                String phoneNumber, UserStatus status, UserRole role){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.role = role;

    }

    //비즈니스 로직

    //연관관계 메소드
    private void foreignHotel(Hotel hotel){
        //호텔 가입
    }
    private void upDateInfo(User user){

    }
    private void changeStatus(String Status){

    }
}
