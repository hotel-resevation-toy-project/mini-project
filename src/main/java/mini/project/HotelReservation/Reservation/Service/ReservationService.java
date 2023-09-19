package mini.project.HotelReservation.Reservation.Service;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Dto.DiscountPriceDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationResponseDto;
import mini.project.HotelReservation.enumerate.DiscountPolicy;

import java.util.List;

public interface ReservationService {
    List<Hotel> findByHotelList();
    List<Hotel> findByRoomList();
    DiscountPriceDto priceCalculator(ReservationRequestDto requestDto);
    Integer discountPrice(DiscountPolicy discountPolicy,
                          Integer reservePrice,
                          Integer days);
    ReservationResponseDto reserve(ReservationRequestDto requestDto,
                                   DiscountPriceDto discountPriceDto);
    ReservationResponseDto reserveInfo(String reserveNumber);
    void reserveDelete(String reserveNumber);
}