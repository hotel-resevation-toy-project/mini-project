package mini.project.HotelReservation.Reservation.Service;


import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.DiscountPolicy.DaysDiscountPolicy.DaysDiscountPolicy;
import mini.project.HotelReservation.DiscountPolicy.PeakDiscountPolicy.PeakDiscountPolicy;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Dto.DiscountPriceDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationRequestDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationResponseDto;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

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
    }

    //예약
    @Override
    public ReservationResponseDto reserve(ReservationRequestDto reservationReqDto, DiscountPriceDto discountPriceDto) {

        //호텔 객체 생성
        Hotel hotel = reservationRepository.findByHotelName(reservationReqDto.getHotelName());

        // todo: ? 에 예약 순서 들어가야함
        String reserveNumber =
                reservationReqDto.getHotelName()+ reservationReqDto.getRoomType().toString()
                + "?" + reservationReqDto.getCheckInDate().toLocalDate().toString();

        User user = userRepository.findById(td.currentUser().get().getUserId()).orElseThrow(
                () -> new NoSuchElementException("해당 유저를 찾을 수 없습니다.")
        );

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
        //reserveNumber(reserve_id) db에서 걍 바로 삭제

    }
}
