package mini.project.HotelReservation.Host.Data.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;

@Data
@RequiredArgsConstructor
public class HotelReservationDto {
    private final String reserveNumber;
    private final String userName;
    private final String userPhoneNumber;


    public HotelReservationDto(Reservation reservation) {
        reserveNumber = reservation.getReserveNumber();
        userName = reservation.getUserName();
        userPhoneNumber = reservation.getPhoneNumber();
    }
}
