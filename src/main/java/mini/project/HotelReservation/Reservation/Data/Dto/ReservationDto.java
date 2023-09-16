package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationDto {

    private String reserveNumber;
    private Integer reservePrice;
    private RoomType roomType;
    private String hotelName;
    private String phoneNumber;
    private String userName;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    private User user;
    private Room room;

}
