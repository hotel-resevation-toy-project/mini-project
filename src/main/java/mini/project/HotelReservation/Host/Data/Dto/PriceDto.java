package mini.project.HotelReservation.Host.Data.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.enumerate.RoomType;

@Data
@RequiredArgsConstructor
public class PriceDto {
    private final RoomType roomType;
    private final Integer discountPrice;
}
