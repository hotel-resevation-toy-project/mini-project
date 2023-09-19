package mini.project.HotelReservation.Reservation.Service;


import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.DiscountPolicy.DaysDiscountPolicy.DaysDiscountPolicy;
import mini.project.HotelReservation.DiscountPolicy.PeakDiscountPolicy.PeakDiscountPolicy;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Reservation.Data.Dto.DiscountPriceDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationResponseDto;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    private final PeakDiscountPolicy peakDiscountPolicy;
    private final DaysDiscountPolicy daysDiscountPolicy;

    private final TokenDecoder td;

    @Override
    public List<Hotel> findByHotelList() {
        return null;
    }

    @Override
    public List<Hotel> findByRoomList() {
        return null;
    }

    @Override
    public DiscountPriceDto priceCalculator(ReservationRequestDto requestDto) {
    }

    @Override
    public Integer discountPrice(DiscountPolicy discountPolicy,
                                 Integer reservePrice,
                                 Integer days){

        switch (discountPolicy.toString()){
            case "POLICY_PEAK":
                return peakDiscountPolicy.discount(reservePrice,days);
            case "POLICY_DAYS":
                return daysDiscountPolicy.discount(reservePrice,days);
            default:
                return Math.max(peakDiscountPolicy.discount(reservePrice, days), daysDiscountPolicy.discount(reservePrice, days));
        }
    }

    //예약 상세 정보
    @Override
    public ReservationResponseDto reserveInfo(String reserveNumber) {
        Reservation reservation = reservationRepository.findByReserveNumber(reserveNumber);

        return new ReservationResponseDto(
                reservation.getRoomType(),
                reservation.getHotelName(),
                reservation.getReservePrice(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );
    }

    //예약 취소
    @Override
    public void reserveDelete(String reserveNumber) {
        reservationRepository.deleteByReserveNumber(reserveNumber);
    }
}
