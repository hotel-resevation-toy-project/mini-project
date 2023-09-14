package mini.project.HotelReservation.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.Data.Enum.DiscountPolicy;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Hotel {
    @Id @GeneratedValue
    private Long hotelId;

    @NotNull
    private String address;

    @NotNull
    private String hotelName;

    @NotNull
    private String hotelPhoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

    @NotNull
    private LocalDateTime checkInTime;

    @NotNull
    private LocalDateTime checkOutTime;

    @NotNull
    private LocalDateTime startPeakTime;

    @NotNull
    private LocalDateTime endPeakTime;

    @OneToMany(mappedBy="hotel")
    List<Room> rooms;


    public Hotel(String address, String hotelName, String hotelPhoneNumber, String discountPolicy, LocalDateTime checkInTime, LocalDateTime checkOutTime, LocalDateTime startPeakTime, LocalDateTime endPeakTime) {
        this.address = address;
        this.hotelName = hotelName;
        this.hotelPhoneNumber = hotelPhoneNumber;
        this.discountPolicy = discountPolicy;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.startPeakTime = startPeakTime;
        this.endPeakTime = endPeakTime;
    }

    //비즈니스 로직
    private void changePolicy(){}

}
