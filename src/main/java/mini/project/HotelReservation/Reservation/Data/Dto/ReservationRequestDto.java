package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.*;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
