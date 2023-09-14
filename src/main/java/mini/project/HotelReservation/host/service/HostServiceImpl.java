package mini.project.HotelReservation.host.service;

import mini.project.HotelReservation.host.data.PriceDto;

public class HostServiceImpl implements HostService{
    private UserRepository userRepository;
    private TokenDecoder td;
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
