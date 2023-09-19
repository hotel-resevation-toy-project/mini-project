package mini.project.HotelReservation.Reservation.Service;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
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
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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
    public DiscountPriceDto priceCalculator() {
        return new DiscountPriceDto();
    //1박당 가격, 숙박일수
    }

    //예약
    @Override
    public ReservationResponseDto reserve(ReservationRequestDto reservationReqDto, DiscountPriceDto discountPriceDto) {

        //호텔 객체 생성
        Hotel hotel = hotelRepository.findByHotelName(reservationReqDto.getHotelName());

        //숙박일
        int days = reservationReqDto.getCheckOutDate().toLocalDate().compareTo(reservationReqDto.getCheckInDate().toLocalDate());

        //할인될 값
        int totalDiscount=0;

        switch (hotel.getDiscountPolicy().toString()){
            case "POLICY_PEAK":
                totalDiscount = peakDiscountPolicy.discount(reservationReqDto.getPrice(),days);
                break;
            case "POLICY_DAYS":
                totalDiscount = daysDiscountPolicy.discount(reservationReqDto.getPrice(),days);
                break;
            case "POLICY_ALL":
                int peakDiscount = peakDiscountPolicy.discount(reservationReqDto.getPrice(),days);
                int daysDiscount = daysDiscountPolicy.discount(reservationReqDto.getPrice(),days);
                totalDiscount = Math.max(peakDiscount, daysDiscount);
                break;
        }


        return new ReservationResponseDto(
                reservationReqDto.getRoomType(),
                reservationReqDto.getHotelName(),
                discountPrice(reservationReqDto.getPrice()),
                reservationReqDto.getCheckInDate(),
                reservationReqDto.getCheckOutDate()
        );
    }

    @Override
    public Integer discountPrice(Integer reservePrice){
        return reservePrice;
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
