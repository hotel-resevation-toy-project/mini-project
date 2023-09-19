package mini.project.HotelReservation.Reservation.Service;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Dto.DiscountPriceDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationResponseDto;

import java.util.List;

public interface ReservationService {
    List<Hotel> findByHotelList();
    List<Hotel> findByRoomList();
    DiscountPriceDto priceCalculator();
    Integer discountPrice(Integer reservePrice);
    ReservationResponseDto reserve(ReservationRequestDto reservationReqDto,
                                   DiscountPriceDto discountPriceDto);
    ReservationResponseDto reserveInfo(String reserveNumber);
    void reserveDelete(String reserveNumber);
}
