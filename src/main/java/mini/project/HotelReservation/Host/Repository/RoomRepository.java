package mini.project.HotelReservation.Host.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findAllByHotel_HotelIdAndRoomType(Long hotelId, RoomType roomType);
    @Query("SELECT r FROM Room r " +
            "JOIN Hotel h " +
            "WHERE r.hotel.hotelId = h.hotelId " +
            "AND h.hotelName = :hotelName")
    List<Room> findAllByHotelName(String hotelName);

}
