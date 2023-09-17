package mini.project.HotelReservation.Reservation.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.enumerate.RoomType;

import java.util.List;

public interface ReservationJoinHotelRepository {

    Hotel findByHotelName(String hotelName);
    List<Reservation> findReservationListByHotelNameAndRoomType(String hotelName, RoomType roomType);
    Long findReservationCountByHotelNameAndRoomType(String hotelName, RoomType roomType);
    List<Reservation> findByHotelId(Long id);
}
