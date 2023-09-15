package mini.project.HotelReservation.Host.Service;

import mini.project.HotelReservation.Host.Data.Dto.PriceDto;
import mini.project.HotelReservation.Host.Data.Enum.DiscountPolicy;
import mini.project.HotelReservation.Reservation.Data.Dto.ReserveDto;

import java.util.List;

public interface HostService {
    void changePolicy(DiscountPolicy policy);
    void modifyRoomPrice(PriceDto priceDto);
    void plusRoomStock(String roomType);
    void minusRoomStock(String roomType);
    List<ReserveDto> reserveList();
}
