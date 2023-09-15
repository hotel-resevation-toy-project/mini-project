package mini.project.HotelReservation.Host.Data.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.AuditTime;
import mini.project.HotelReservation.Host.Data.Enum.DiscountPolicy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "HOTELS")
public class Hotel extends AuditTime {
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
    private LocalTime checkInTime;

    @NotNull
    private LocalTime checkOutTime;

    @NotNull
    private LocalDate startPeakDate;

    @NotNull
    private LocalDate endPeakDate;

    @OneToMany(mappedBy="hotel")
    List<Room> rooms;


    public Hotel(String address, String hotelName, String hotelPhoneNumber, DiscountPolicy discountPolicy, LocalTime checkInTime, LocalTime checkOutTime, LocalDate startPeakDate, LocalDate endPeakDate) {
        this.address = address;
        this.hotelName = hotelName;
        this.hotelPhoneNumber = hotelPhoneNumber;
        this.discountPolicy = discountPolicy;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.startPeakDate = startPeakDate;
        this.endPeakDate = endPeakDate;
    }

    //비즈니스 로직
    public void changePolicy(DiscountPolicy dis){
        discountPolicy = dis;
    }

}
