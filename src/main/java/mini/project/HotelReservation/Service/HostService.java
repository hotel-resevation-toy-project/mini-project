package mini.project.HotelReservation.Service;

import mini.project.HotelReservation.Data.Dto.PriceDto;
import mini.project.HotelReservation.Data.Dto.ReserveDto;
import mini.project.HotelReservation.Data.Enum.DiscountPolicy;

import java.util.List;

public interface HostService {
    void changePolicy(DiscountPolicy policy);
    void modifyRoomPrice(PriceDto priceDto);
    void plusRoomStock(String roomType);
    void minusRoomStock(String roomType);
    List<ReserveDto> reserveList();
}
