package mini.project.HotelReservation.Reservation.Service;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReserveDto;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

/*
    - reservationRepository -
    Reservation findByReserveNumber(String reserveNumber);
    List<Reservation> findAllByUser_UserId(Long userId);
    Hotel findByHotelName(String hotelName);
    void deleteByReserveNumber(String reserveNumber);

    - Reservation -
    Long reserveId;
    String reserveNumber;
    Integer reservePrice;
    RoomType roomType;
    String hotelName;
    String phoneNumber;
    String userName;
    LocalDateTime checkInDate;
    LocalDateTime checkOutDate;
    User user;
    Room room;

*/
    private final PeakDiscountPolicy peakDiscountPolicy;
    private final DaysDiscountPolicy daysDiscountPolicy;

    private final TokenDecoder td;
//td.tokenToIds(String token);
    //예약
    @Override
    public ReservationDto reserve(ReserveDto reserveDto) {
/*
        - ReserveDto -
         RoomType roomType;
         String hotelName;
         Integer reservePrice;
         LocalDateTime checkInDate;
         LocalDateTime checkOutDate;
*/
        //호텔 객체 생성
        Hotel hotel =
                reservationRepository.findByHotelName(reserveDto.getHotelName());

        /*[호텔명 + 객실종류 +예약 순서 + 입실 년,월,일]
        예약 순서 → 타입별 전체 객실 수 - 타입별 남은 객실 수 = 타입별 예약 순서
        */

        // todo: ? 에 예약 순서 들어가야함
        String reserveNumber =
        reserveDto.getHotelName()+reserveDto.getRoomType().toString()
                + "?" + reserveDto.getCheckInDate().toLocalDate().toString();

//        List<Reservation> findAllByUser_UserId(Long userId)


        // todo: 유저이름 가져오자
        Reservation reservation =
                new Reservation(
                        reserveNumber,
                        discountPrice(reserveDto.getReservePrice()),
                        reserveDto.getRoomType(),
                        reserveDto.getHotelName(),
                        hotel.getHotelPhoneNumber(),
                        reservationList.get(0).getUserName(),
                        reserveDto.getCheckInDate(),
                        reserveDto.getCheckOutDate()
                );



        return null;
    }


    @Override
    public Integer discountPrice(Integer reservePrice){



        return reservePrice;
    }


    //해당 유저의 예약 리스트
    @Override
    public List<ReservationDto> reservations() {
        return reservationRepository.findAllByUser_UserId(td.tokenToIds(String token));
    }

    //예약 상세 정보
    @Override
    public ReservationDto reserveInfo(String reserveNumber) {
        return null;
    }

    //예약 취소
    @Override
    public void reserveDelete(String reserveNumber) {
        

    }
}
