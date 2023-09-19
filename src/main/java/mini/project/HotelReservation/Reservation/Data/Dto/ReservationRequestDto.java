package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationRequestDto {
    private String hotelName;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private RoomType roomType;
    private Integer price;
}
