package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.enumerate.RoomType;

@Data
@RequiredArgsConstructor
public class RoomDto {
    private final RoomType roomType;
    private final Integer roomPrice;
    private final Integer roomStock;
}
