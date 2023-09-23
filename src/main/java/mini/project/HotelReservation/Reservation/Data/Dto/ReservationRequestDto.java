package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.*;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private RoomType roomType;
    private Integer oneDayPrice;

}
