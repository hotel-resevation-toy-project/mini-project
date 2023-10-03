package mini.project.HotelReservation.Host.Data.Dto;

import lombok.Data;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
@Data
public class HotelReservationDto {
    private String reserveNumber;
    private String userName;
    private String userPhoneNumber;

    public HotelReservationDto(Reservation reservation) {
        reserveNumber = reservation.getReserveNumber();
        userName = reservation.getUserName();
        userPhoneNumber = reservation.getPhoneNumber();
    }
}
