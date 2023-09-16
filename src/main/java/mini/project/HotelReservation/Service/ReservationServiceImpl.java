package mini.project.HotelReservation.Service;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Data.Dto.ReservationDto;
import mini.project.HotelReservation.Data.Dto.ReserveDto;
import mini.project.HotelReservation.Data.Entity.Hotel;
import mini.project.HotelReservation.Repository.ReservationRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    
    private final PeakDiscountPolicy peakDiscountPolicy;
    private final DaysDiscountPolicy daysDiscountPolicy;

    private final TokenDecoder td;

    //예약
    @Override
    public ReservationDto reserve(ReserveDto reserveDto) {

        //호탤 객체
        Hotel hotel =
                reservationRepository.findByHotelName(reserveDto.getHotelName());

        Long hotelId = hotel.getHotelId();

        //할인 적용된 숙박비
        Integer reservePrice;

            switch (hotel.getDiscountPolicy().toString()) {
                case "POLICY_PEAK": //성수기할인
                    reservePrice = policyPeak(reserveDto,hotel);
                    break;
                case "POLICY_DAYS": //연박할인
                    reservePrice = policyDays(reserveDto,hotel);
                    break;
                case "POLICY_ALL": //둘다적용
                    reservePrice =
                    break;
            }//end of switch
        return null;
    }//end of reserve Method

    //POLICY_PEAK 선택시 계산
    public Integer policyPeak(ReserveDto reserveDto, Hotel hotel){

        //연박 일 수 확인
        int stayDays = reserveDto.getCheckOutDate().toLocalDate().compareTo(reserveDto.getCheckInDate().toLocalDate()); //a.compareTo(b) a기준 : 동일0 큼+ 작음-
        //할인 적용된 숙박비
        Integer reservePrice;

        //성수기에 해당하는 날짜에 숙박하는지 확인
        if(reserveDto.getCheckInDate().toLocalDate().compareTo(hotel.getStartPeakDate())>=0){// 체크인 날짜가 성수기 시작일보다 뒤인지 확인

            if(hotel.getEndPeakDate().compareTo(reserveDto.getCheckOutDate().toLocalDate())>=0) {//체크아웃 날짜가 성수기 끝나는 날보다 앞인지 확인

                reservePrice = reserveDto.getReservePrice()*stayDays;

                if(reservePrice>=30){
                    reservePrice -= 10;
                }

            }else{//성수기에 체크인 & 비성수기에 체크아웃

                reservePrice = reserveDto.getReservePrice()*
                        (stayDays + (hotel.getEndPeakDate().compareTo(reserveDto.getCheckOutDate().toLocalDate())));

                if(reservePrice >= 30){

                    reservePrice -= 10;
                    reservePrice += -(hotel.getEndPeakDate().compareTo(reserveDto.getCheckOutDate().toLocalDate()))*
                            reserveDto.getReservePrice();

                }else {
                    reservePrice = reserveDto.getReservePrice()*stayDays;
                }
            }
        }else {//성수기에 해당하지 않음
            reservePrice = reserveDto.getReservePrice()*stayDays;
        }
        return reservePrice;
    }
    
    //POLICY_DAYS 선택시 계산
    public Integer policyDays(ReserveDto reserveDto, Hotel hotel){
        //연박 일 수 확인
        int stayDays = reserveDto.getCheckOutDate().toLocalDate().compareTo(reserveDto.getCheckInDate().toLocalDate()); //a.compareTo(b) a기준 : 동일0 큼+ 작음-
        //할인 적용된 숙박비
        Integer reservePrice;

        if (stayDays >= 3) {
            //- 정률 할인 5% (int)(Math.pow(원가 * 0.95, 일 수 / 3))
            reservePrice = (int) Math.pow(reserveDto.getReservePrice()*0.95, stayDays/3);

        }else{//3일 미만 숙박
            reservePrice = reserveDto.getReservePrice()*stayDays;
        }

        return reservePrice;
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
