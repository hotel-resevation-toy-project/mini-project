package mini.project.HotelReservation.reserve.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.reserve.entity.RoomType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReserveDto {
    private RoomType roomType;
    private String hotelName;
    private Integer reservePrice;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
}
