package mini.project.HotelReservation.Reservation.Service;


import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.DiscountPolicy.DaysDiscountPolicy.DaysDiscountPolicy;
import mini.project.HotelReservation.DiscountPolicy.PeakDiscountPolicy.PeakDiscountPolicy;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReserveDto;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
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

    private final PeakDiscountPolicy peakDiscountPolicy;
    private final DaysDiscountPolicy daysDiscountPolicy;

    private final TokenDecoder td;

    //예약
    @Override
    public ReservationDto reserve(ReserveDto reserveDto) {

        //호텔 객체 생성
        Hotel hotel = reservationRepository.findByHotelName(reserveDto.getHotelName());
        /*
        [호텔명 + 객실종류 +예약 순서 + 입실 년,월,일]
        예약 순서 → 타입별 전체 객실 수 - 타입별 남은 객실 수 = 타입별 예약 순서
        */

        // todo: ? 에 예약 순서 들어가야함
        String reserveNumber =
        reserveDto.getHotelName()+reserveDto.getRoomType().toString()
                + "?" + reserveDto.getCheckInDate().toLocalDate().toString();

        User user = userRepository.findById(td.).orElseThrow(
                () -> new NoSuchElementException("해당 유저를 찾을 수 없습니다.")
        );

        Reservation reservation = Reservation.builder()
                .userName(user.getName())
                .hotelName(reserveDto.getHotelName())
                .roomType(reserveDto.getRoomType())
                .phoneNumber(user.getPhoneNumber())
                .checkInDate(reserveDto.getCheckInDate())
                .checkOutDate(reserveDto.getCheckOutDate())
                .reservePrice(discountPrice(reserveDto.getReservePrice()))
                .build();


        return new ReservationDto(
                reservation.getUserName(),
                reservation.getPhoneNumber(),
                reservation.getHotelName(),
                reservation.getRoomType(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getReserveNumber(),
                reservation.getReservePrice()
        );
    }


    @Override
    public Integer discountPrice(Integer reservePrice){

        return reservePrice;
    }

    //해당 유저의 예약 리스트
    @Override
    public List<ReservationDto> reservations() {
        return reservationRepository.findAllByUser_UserId(td.currentUser.get().getUserId());
    }

    //예약 상세 정보
    @Override
    public ReservationDto reserveInfo(String reserveNumber) {
        Reservation reservation = reservationRepository.findByReserveNumber(reserveNumber);
        // reservationDto로 옮겨 담기

        return new ReservationDto(
                reservation.getUserName(),
                reservation.getPhoneNumber(),
                reservation.getHotelName(),
                reservation.getRoomType(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getReserveNumber(),
                reservation.getReservePrice()
        );
    }

    //예약 취소
    @Override
    public void reserveDelete(String reserveNumber) {
        //reserveNumber(reserve_id) db에서 걍 바로 삭제
    }
}
