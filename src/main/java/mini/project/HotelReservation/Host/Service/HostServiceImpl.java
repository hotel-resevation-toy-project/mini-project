package mini.project.HotelReservation.Host.Service;

import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Dto.HotelReservationResponseDto;
import mini.project.HotelReservation.Host.Data.Dto.PriceDto;
import mini.project.HotelReservation.Host.Data.Dto.RoomStockDto;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.enumerate.RoomType;
import java.util.Optional;

public class HostServiceImpl implements HostService {
    private HotelRepository hotelRepository;

    private RoomRepository roomRepository;

    private TokenDecoder td;
    @Override
    public void changePolicy(DiscountPolicy policy) {
        Long hotelId = 1L/*td.asdfasd()*/;
        Hotel hotel = hotelRepository.findByHotelId(hotelId);
        hotel.changePolicy((DiscountPolicy) policy);

    }

    @Override
    public void modifyRoomPrice(PriceDto priceDto) {
        Long hotelId = 1L/*td.asdfasd()*/;
        RoomType roomType = priceDto.getRoomType();
        Room room = roomRepository.findByHotel_HotelIdAndRoomType(hotelId, roomType);

        room.modifyPrice(priceDto.getDiscountPrice());
    }

    @Override
    public void modifyRoomStock(RoomStockDto roomStockDto) {
        Long hotelId = 1L/*td.asdfasd()*/;
        RoomType roomType =roomStockDto.getRoomType();
        Room room = roomRepository.findByHotel_HotelIdAndRoomType(hotelId, roomType);

        room.modifyStock(roomStockDto.getRoomStock());
    }

    @Override
    public Optional<HotelReservationResponseDto> reserveList(Long hotelId) {
        return hotelRepository.findReservationByHotelId(hotelId);
    }
}
