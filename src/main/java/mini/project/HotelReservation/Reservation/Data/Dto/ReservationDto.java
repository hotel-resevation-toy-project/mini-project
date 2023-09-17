package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationDto {

    private String userName;
    private String phoneNumber;
    private String hotelName;
    private RoomType roomType;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String reserveNumber;
    private Integer reservePrice;


}
