package mini.project.HotelReservation.Repository;


import mini.project.HotelReservation.Data.Entity.Room;
import mini.project.HotelReservation.Data.Enum.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByHotel_HotelIdAndRoomType(Long hotelId, RoomType roomType);
}
