package mini.project.HotelReservation.Host.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;

@Data
@AllArgsConstructor
public class HotelReservationDto {
    private final String reserveNumber;
    private final String userName;
    private final String userPhoneNumber;


    public HotelReservationDto(Reservation reservation) {
        this.reserveNumber = reservation.getReserveNumber();
        this.userName = reservation.getUserName();
        this.userPhoneNumber = reservation.getPhoneNumber();
    }
}
