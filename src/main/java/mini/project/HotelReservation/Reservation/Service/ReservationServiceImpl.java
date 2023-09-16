package mini.project.HotelReservation.Reservation.Service;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReserveDto;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    
/*
    private final PeakDiscountPolicy peakDiscountPolicy;
    private final DaysDiscountPolicy daysDiscountPolicy;

    private final TokenDecoder td;
*/

    //예약
    @Override
    public ReservationDto reserve(ReserveDto reserveDto) {




        return null;
    }


    @Override
    public Integer discountPrice(Integer reservePrice) {

        return reservePrice;
    }


    //해당 유저의 예약 리스트
    @Override
    public List<ReservationDto> reservations() {


        return null;
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
