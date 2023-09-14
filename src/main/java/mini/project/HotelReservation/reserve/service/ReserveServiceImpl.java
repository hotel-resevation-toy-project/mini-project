package mini.project.HotelReservation.reserve.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.reserve.data.ReservationDto;
import mini.project.HotelReservation.reserve.data.ReserveDto;
import mini.project.HotelReservation.reserve.entity.Reserve;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements ReserveService{

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

    }
}
