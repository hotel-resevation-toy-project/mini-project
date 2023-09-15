package mini.project.HotelReservation.Service;

import mini.project.HotelReservation.Data.Dto.PriceDto;
import mini.project.HotelReservation.Data.Dto.ReserveDto;
import mini.project.HotelReservation.Repository.UserRepository;
import mini.project.HotelReservation.Service.HostService;

import java.util.List;

public class HostServiceImpl implements HostService {
    private UserRepository userRepository;
//    private TokenDecoder td;
    @Override
    public void changePolicy(String policy) {

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
