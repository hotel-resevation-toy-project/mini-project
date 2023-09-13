package mini.project.HotelReservation.host.repository;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByHotelIdAndRoomType(Long hotelId, RoomType roomType);
}
