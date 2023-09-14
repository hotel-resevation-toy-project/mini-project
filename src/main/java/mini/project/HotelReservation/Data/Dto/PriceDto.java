package mini.project.HotelReservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.Data.Enum.RoomType;

@Data
@AllArgsConstructor
public class PriceDto {
    private RoomType roomType;
    private Integer roomPrice;
}
