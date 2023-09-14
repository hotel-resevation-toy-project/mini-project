package mini.project.HotelReservation.reserve.service;

import mini.project.HotelReservation.reserve.data.ReservationDto;
import mini.project.HotelReservation.reserve.data.ReserveDto;

import java.util.List;

public interface ReserveService {
    ReservationDto reserve(ReserveDto reserveDto);
    Integer reservePrice();
    void getHotel();
    List<ReservationDto> reservations();
    ReservationDto reserveInfo(String reserveNumber);
    void reserveDelete(String reserveNumber);
}
