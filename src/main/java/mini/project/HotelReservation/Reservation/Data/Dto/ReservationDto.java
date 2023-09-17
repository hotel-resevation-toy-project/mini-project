package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.User.Data.Entity.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationDto {

    private String userName;
    private Integer userPhoneNumber;
    private String hotelName;
    private String roomType;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String reserveNumber;
    private Integer totalPrice;


}
