package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.Data;
import mini.project.HotelReservation.enumerate.RoomType;

@Data
public class RoomDto {
    private final String roomType;
    private final Integer roomPrice;
    private final Integer roomStock;

    public RoomDto(RoomType roomType, Integer roomPrice, Integer roomStock){
        this.roomType = String.valueOf(roomType);
        this.roomPrice = roomPrice;
        this.roomStock = roomStock;
    }

}
