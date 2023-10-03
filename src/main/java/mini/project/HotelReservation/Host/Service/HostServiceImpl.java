package mini.project.HotelReservation.Host.Service;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Dto.HotelReservationDto;
import mini.project.HotelReservation.Host.Data.Dto.PriceDto;
import mini.project.HotelReservation.Host.Data.Dto.RoomStockDto;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final TokenDecoder td;

    //호텔 이름 가져오기
    @Override
    public String referenceHotel(){
        return td.currentUser().getHotel().getHotelName();
    }

    @Override
    @Transactional
    public void changePolicy(DiscountPolicy policy) {
        Long hotelId = td.currentUser().getHotel().getHotelId();
        Hotel hotel = hotelRepository.findByHotelId(hotelId);
        hotel.changePolicy(policy);
    }

    @Override
    @Transactional
    public void modifyRoomPrice(PriceDto priceDto) {
        Long hotelId = td.currentUser().getHotel().getHotelId();

        Room room = roomRepository.findByHotel_HotelIdAndRoomType(hotelId,
                selectRoomType(priceDto.getRoomType()));

        room.modifyPrice(priceDto.getDiscountPrice());
    }

    @Override
    @Transactional
    public void modifyRoomStock(RoomStockDto roomStockDto) {
        Long hotelId = td.currentUser().getHotel().getHotelId();

        Room room = roomRepository.findByHotel_HotelIdAndRoomType(hotelId,
                selectRoomType(roomStockDto.getRoomType()));

        room.modifyStock(roomStockDto.getRoomStock());
    }

    @Override
    public List<HotelReservationDto> reservationList() {
        Long hotelId = td.currentUser().getHotel().getHotelId();
        return reservationRepository
                .findAllByHotel_HotelId(hotelId)
                .stream().map(HotelReservationDto::new).toList();
    }

    public RoomType selectRoomType(String roomType) {
        return switch (roomType) {
            case "A" -> RoomType.ROOM_TYPE_A_SINGLE;
            case "B" -> RoomType.ROOM_TYPE_B_TWIN;
            case "C" -> RoomType.ROOM_TYPE_C_QUEEN;
            case "D" -> RoomType.ROOM_TYPE_D_KING;
            default -> null;
        };
    }
}