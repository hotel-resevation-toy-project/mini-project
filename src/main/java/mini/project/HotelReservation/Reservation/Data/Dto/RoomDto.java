package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.Data;

@Data
public class RoomDto {
    private final String roomType;
    private final Integer roomPrice;
    private final Integer roomStock;

    public RoomDto(String roomType, Integer roomPrice, Integer roomStock){
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomStock =roomStock;
    }

}
