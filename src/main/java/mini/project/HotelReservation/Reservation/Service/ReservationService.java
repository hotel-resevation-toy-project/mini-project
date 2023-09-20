package mini.project.HotelReservation.Reservation.Service;

import mini.project.HotelReservation.Reservation.Data.Dto.*;
import mini.project.HotelReservation.enumerate.DiscountPolicy;

import java.util.List;

public interface ReservationService {
    List<HotelDto> findByHotelList();
    List<RoomDto> findByRoomList(String hotelName);
    DiscountPriceDto discountPrice(ReservationRequestDto requestDto);
    ReservationResponseDto reserve(ReservationRequestDto requestDto,
                                   DiscountPriceDto discountPriceDto);
    ReservationResponseDto reserveInfo(String reserveNumber);
    void reserveDelete(String reserveNumber);
}
