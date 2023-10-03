package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private RoomType roomType;

    public ReservationRequestDto(String hotelName, DateDto dateDto, RoomType roomType){
        this.hotelName = hotelName;
        this.checkInDate = dateDto.getCheckInDate();
        this.checkOutDate = dateDto.getCheckOutDate();
        this.roomType = roomType;
    }

}
