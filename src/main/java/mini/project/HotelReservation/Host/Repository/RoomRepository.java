package mini.project.HotelReservation.Host.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByHotel_HotelIdAndRoomType(Long hotelId, RoomType roomType);
    @Query("SELECT r FROM Room r " +
            "JOIN r.hotel h " +
            "WHERE h.hotelName = :hotelName AND r.roomType = :roomType")
    Room findByHotelNameAndRoomType(@Param("hotelName") String hotelName, @Param("roomType") RoomType roomType);
    @Query("SELECT r FROM Room r " +
            "JOIN r.hotel h " +
            "WHERE h.hotelName = :hotelName")
    List<Room> findAllByHotelName(@Param("hotelName")String hotelName);
}
