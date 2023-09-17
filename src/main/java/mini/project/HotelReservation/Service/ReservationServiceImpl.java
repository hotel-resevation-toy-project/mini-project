package mini.project.HotelReservation.Service;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Reservation.Data.Dto.ReservationDto;
import mini.project.HotelReservation.Reservation.Data.Dto.ReserveDto;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.Reservation.Service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
//    !! 아직 없는 정책이라 빨간줄이라서 묶어둠
    /*    private final PeakDiscountPolicy peakDiscountPolicy;
    private final DaysDiscountPolicy daysDiscountPolicy;*/


//    private final TokenDecoder td;

    @Override
    public ReservationDto reserve(ReserveDto reserveDto) {
        return null;
    }

    @Override
    public Integer reservePrice() {
        return null;
    }

    @Override
    public void getHotel() {

    }

    @Override
    public List<ReservationDto> reservations() {
        return null;
    }

    @Override
    public ReservationDto reserveInfo(String reserveNumber) {
        return null;
    }

    @Override
    public void reserveDelete(String reserveNumber) {
