package mini.project.HotelReservation.Host.Service;

import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Dto.PriceDto;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.Reservation.Data.Dto.ReserveDto;

import java.util.List;

public class HostServiceImpl implements HostService {
    private HotelRepository hotelRepository;
    private TokenDecoder td;
    @Override
    public void changePolicy(DiscountPolicy policy) {
        Long hotelId = 1L/*td.asdfasd()*/;
        Hotel hotel = hotelRepository.findByHotelId(hotelId);
        hotel.changePolicy((DiscountPolicy) policy);

    }

    @Override
    public void modifyRoomPrice(PriceDto priceDto) {

    }

    @Override
    public void plusRoomStock(String roomType) {

    }

    @Override
    public void minusRoomStock(String roomType) {

    }

    @Override
    public List<ReserveDto> reserveList() {
        return null;
    }
}
