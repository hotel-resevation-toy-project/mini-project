package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ReservationResponseDto {
    private final String userName;
    private final String phoneNumber;
    private final String hotelName;
    private final RoomType roomType;
    private final LocalDateTime checkInDate;
    private final LocalDateTime checkOutDate;
    private final String reserveNumber;
    private final Integer reservePrice;
}
