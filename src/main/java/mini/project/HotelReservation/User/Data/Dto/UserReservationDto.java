package mini.project.HotelReservation.User.Data.Dto;

import lombok.Data;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;

import java.time.LocalDateTime;

@Data
public class UserReservationDto {
    private final String reserveNumber;
    private final String hotelName;
    private final LocalDateTime checkInDate;
    private final LocalDateTime checkOutDate;

    public UserReservationDto(Reservation reservation){
        this.reserveNumber = reservation.getReserveNumber();
        this.hotelName = reservation.getHotelName();
        this.checkInDate = reservation.getCheckInDate();
        this.checkOutDate = reservation.getCheckOutDate();
    }
}
