package mini.project.HotelReservation.Data.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String discountPolicy;

    @NotNull
    private LocalDateTime checkInTime;

    @NotNull
    private LocalDateTime checkOutTime;

    @NotNull
    private LocalDateTime startPeakTime;

    @NotNull
    private LocalDateTime endPeakTime;




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
