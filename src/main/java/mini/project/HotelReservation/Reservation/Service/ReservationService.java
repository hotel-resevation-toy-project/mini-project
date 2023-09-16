package mini.project.HotelReservation.Reservation.Service;

import mini.project.HotelReservation.Reservation.Data.Dto.ReservationDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReserveDto;

import java.util.List;

public interface ReservationService {
    ReservationDto reserve(ReserveDto reserveDto);
    Integer reservePrice();
    void getHotel();
    List<ReservationDto> reservations();
    ReservationDto reserveInfo(String reserveNumber);
    void reserveDelete(String reserveNumber);
}
