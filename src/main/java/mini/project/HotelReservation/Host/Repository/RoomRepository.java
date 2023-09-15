package mini.project.HotelReservation.Host.Repository;


import mini.project.HotelReservation.Host.Data.Entity.Room;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByHotel_HotelIdAndRoomType(Long hotelId, RoomType roomType);
}
