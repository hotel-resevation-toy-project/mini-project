package mini.project.HotelReservation.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @NotNull
    @Column(name = "user_id")
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
    @Enumerated(EnumType.STRING)
    private Hotel hotel;

    public User(Long userId,
                String name,
                String email,
                String password,
                String phoneNumber,
                UserStatus status,
                UserRole role){
        this.userId = userId;
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
