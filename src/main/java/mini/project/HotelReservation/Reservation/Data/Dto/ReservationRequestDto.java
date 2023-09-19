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
    /*private String checkInDate;
    private String checkOutDate;*/
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private RoomType roomType;
    private Integer price;
}
