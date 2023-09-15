package mini.project.HotelReservation.Service;

import mini.project.HotelReservation.Data.Dto.PriceDto;
import mini.project.HotelReservation.Data.Dto.ReserveDto;
import mini.project.HotelReservation.Data.Entity.Hotel;
import mini.project.HotelReservation.Data.Enum.DiscountPolicy;
import mini.project.HotelReservation.Repository.HotelRepository;
import java.util.List;

public class HostServiceImpl implements HostService {
    private HotelRepository hotelRepository;
    //todo: jwt 구현후 주석풀기
//    private TokenDecoder td;
    @Override
    public void changePolicy(DiscountPolicy policy) {
        Long hotelId = td.asdasd();
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
