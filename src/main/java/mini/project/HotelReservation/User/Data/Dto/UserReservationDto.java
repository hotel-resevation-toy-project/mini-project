package mini.project.HotelReservation.User.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserReservationDto {
    private final String reserveNumber;
    private final String hotelName;
    private final LocalDateTime checkInDate;
    private final LocalDateTime checkOutDate;
}
