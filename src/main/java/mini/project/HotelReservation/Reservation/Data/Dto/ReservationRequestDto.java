package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ReservationRequestDto {
    private final String hotelName;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final RoomType roomType;
    private final Integer oneDayPrice;
}
