package mini.project.HotelReservation.Repository;

import mini.project.HotelReservation.Data.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Hotel findByHotelName(String hotelName);
    Hotel findByHotelId(Long hotelId);
}
