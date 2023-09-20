package mini.project.HotelReservation.Host.Service;

import mini.project.HotelReservation.Host.Data.Dto.HotelReservationResponseDto;
import mini.project.HotelReservation.Host.Data.Dto.PriceDto;
import mini.project.HotelReservation.Host.Data.Dto.RoomStockDto;
import mini.project.HotelReservation.enumerate.DiscountPolicy;

import java.util.List;

public interface HostService {
    void changePolicy(DiscountPolicy policy);
    void modifyRoomPrice(PriceDto priceDto);
    void modifyRoomStock(RoomStockDto roomStockDto);
    List<HotelReservationResponseDto> reservationList();}
