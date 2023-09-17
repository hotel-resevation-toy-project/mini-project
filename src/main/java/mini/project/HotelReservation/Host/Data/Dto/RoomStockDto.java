package mini.project.HotelReservation.Host.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.enumerate.RoomType;

@Data
@AllArgsConstructor
public class RoomStockDto {
    private RoomType roomType;

    private Integer roomStock;
}
