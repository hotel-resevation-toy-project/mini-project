package mini.project.HotelReservation.User.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserReservationResponseDto {
    private String hotelName;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
}
