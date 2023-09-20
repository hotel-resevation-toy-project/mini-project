package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Builder
public class ReservationResponseDto {
    private final String userName;
    private final String phoneNumber;
    private final String hotelName;
    // TODO: 2023-09-20 RoomType -> String check
    private final RoomType roomType;
    private final LocalDateTime checkInDate;
    private final LocalDateTime checkOutDate;
    private final String reservationNumber;
    private final Integer reservePrice;
}
