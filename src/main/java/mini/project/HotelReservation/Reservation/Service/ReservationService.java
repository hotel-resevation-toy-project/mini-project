package mini.project.HotelReservation.Reservation.Service;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Dto.*;

import java.util.List;

public interface ReservationService {
    List<HotelDto> findByHotelList();
    List<RoomDto> findByRoomList(String hotelName);
    DiscountPriceDto discountPrice(ReservationRequestDto requestDto);
    ReservationResponseDto reserve(ReservationRequestDto requestDto,
                                   DiscountPriceDto discountPriceDto);
    String createReserveNumber(Hotel hotel,
                               ReservationRequestDto reservationReqDto);
    ReservationResponseDto reserveInfo(String reserveNumber);
    void reserveDelete(String reserveNumber);
}
