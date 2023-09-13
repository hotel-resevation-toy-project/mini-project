package mini.project.HotelReservation.host.service;

import mini.project.HotelReservation.host.data.PriceDto;

public interface HostService {
    void changePolicy(String policy);
    void modifyRoomPrice(PriceDto priceDto);
    void plusRoomStock(String roomType);
    void minusRoomStock(String roomType);

    List<ReserveDto> reserveList();
}
