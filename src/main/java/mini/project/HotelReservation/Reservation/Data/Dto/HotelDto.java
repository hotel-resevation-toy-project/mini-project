package mini.project.HotelReservation.Reservation.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.enumerate.RoomType;

import java.time.LocalTime;
import java.util.List;

@Data
public class HotelDto {
    private final String hotelName;
    private final List<RoomType> roomTypes;
    private final LocalTime checkInTime;
    private final LocalTime checkOutTime;

    public HotelDto(Hotel hotel) {
        this.hotelName = hotel.getHotelName();
        this.roomTypes = hotel.getRooms().stream().map(Room::getRoomType).toList();
        this.checkInTime = hotel.getCheckInTime();
        this.checkOutTime = hotel.getCheckOutTime();
    }
}
