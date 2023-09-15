package mini.project.HotelReservation.Host.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.Reservation.Data.Enum.RoomType;

@Data
@AllArgsConstructor
public class PriceDto {
    private RoomType roomType;
    private Integer roomPrice;
}
