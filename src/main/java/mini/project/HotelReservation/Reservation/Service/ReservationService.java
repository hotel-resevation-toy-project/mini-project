package mini.project.HotelReservation.Reservation.Service;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Dto.*;
import mini.project.HotelReservation.enumerate.DiscountPolicy;

import java.util.List;

public interface ReservationService {
    List<HotelDto> findByHotelList();
    List<RoomDto> findByRoomList(String hotelName);
    DiscountPriceDto priceCalculator();
    Integer discountPrice(Integer reservePrice);
    ReservationResponseDto reserve(ReservationRequestDto reservationReqDto,
                                   DiscountPriceDto discountPriceDto);
    ReservationResponseDto reserveInfo(String reserveNumber);
    void reserveDelete(String reserveNumber);
}
