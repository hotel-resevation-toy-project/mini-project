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
import mini.project.HotelReservation.User.Repository.UserRepository;
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
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final TokenDecoder td;

    //호텔 이름 가져오기
    @Override
    public String referenceHotel(){
        return userRepository.findHotelNameByTokenId(td.currentUserId());
    }

    @Override
    @Transactional
    public void changePolicy(DiscountPolicy policy) {
        hotelRepository.findByHotelId(
            userRepository.findHotelIdByTokenId(td.currentUserId())
        ).changePolicy(policy);
    }

    @Override
    @Transactional
    public void modifyRoomPrice(PriceDto priceDto) {
        roomRepository.findByHotel_HotelIdAndRoomType(
            userRepository.findHotelIdByTokenId(td.currentUserId()),
            selectRoomType(priceDto.getRoomType())
        ).modifyPrice(priceDto.getDiscountPrice());
    }

    @Override
    @Transactional
    public void modifyRoomStock(RoomStockDto roomStockDto) {
        roomRepository.findByHotel_HotelIdAndRoomType(
            userRepository.findHotelIdByTokenId(td.currentUserId()),
            selectRoomType(roomStockDto.getRoomType())
        ).modifyStock(roomStockDto.getRoomStock());
    }

    @Override
    public List<HotelReservationDto> reservationList() {
        return reservationRepository.findDtosByHotelId(userRepository.findHotelIdByTokenId(td.currentUserId()));
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