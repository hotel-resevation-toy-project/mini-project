package mini.project.HotelReservation.Host.Service;

import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.Host.Data.Dto.HotelReservationDto;
import mini.project.HotelReservation.Host.Data.Dto.PriceDto;
import mini.project.HotelReservation.Host.Data.Dto.RoomStockDto;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.Host.Repository.RoomRepository;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.Reservation.Repository.ReservationRepository;
import mini.project.HotelReservation.enumerate.DiscountPolicy;
import mini.project.HotelReservation.Host.Repository.HotelRepository;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HostServiceImpl implements HostService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final TokenDecoder td;

    @Override
    public String referenceHotel(){
        String hotelName = td.currentUser().getHotel().getHotelName();
        return hotelName;
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
        RoomType roomType = priceDto.getRoomType();
        Room room = roomRepository.findByHotel_HotelIdAndRoomType(hotelId, roomType);

        room.modifyPrice(priceDto.getDiscountPrice());
    }

    @Override
    @Transactional
    public void modifyRoomStock(RoomStockDto roomStockDto) {
        Long hotelId = td.currentUser().getHotel().getHotelId();
        RoomType roomType = roomStockDto.getRoomType();
        Room room = roomRepository.findByHotel_HotelIdAndRoomType(hotelId, roomType);

        room.modifyStock(roomStockDto.getRoomStock());
    }

    @Override
    public List<HotelReservationDto> reservationList() {
        Long hotelId = td.currentUser().getHotel().getHotelId();
        List<Reservation> reservationsByHotelId = reservationRepository.findAllByHotel_HotelId(hotelId);
        List<HotelReservationDto> reservations = new ArrayList<>();
        for (Reservation reservation : reservationsByHotelId) {
            reservations.add(new HotelReservationDto(reservation.getReserveNumber(),
                                                             reservation.getUserName(),
                                                               reservation.getPhoneNumber()));
        }
        return reservations;
    }

}