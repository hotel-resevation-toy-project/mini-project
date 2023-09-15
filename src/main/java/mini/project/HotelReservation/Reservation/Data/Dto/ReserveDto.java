package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.Reservation.Data.Enum.RoomType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReserveDto {
    private RoomType roomType;
    private String hotelName;
    private Integer reservePrice;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
}
