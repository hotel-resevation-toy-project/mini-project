package mini.project.HotelReservation.Reservation.Service;

import mini.project.HotelReservation.Reservation.Data.Dto.*;
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
