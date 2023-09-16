package mini.project.HotelReservation.User.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.AuditTime;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import org.springframework.data.domain.Persistable;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends AuditTime implements Persistable<Long>{

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

    @Builder
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
    public void changeStatus(){
        this.status = UserStatus.USER_STATUS_ACTIVE;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }

}
