package mini.project.HotelReservation.host.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceDto {
    private RoomType roomType;
    private Integer roomPrice;
}
