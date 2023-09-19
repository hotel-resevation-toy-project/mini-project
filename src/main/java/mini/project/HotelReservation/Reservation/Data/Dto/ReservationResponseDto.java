package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ReservationResponseDto {
    private final RoomType roomType;
    private final String hotelName;
    private final Integer reservePrice;
    private final LocalDateTime checkInDate;
    private final LocalDateTime checkOutDate;
}
