package mini.project.HotelReservation.Host.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HotelRepository extends JpaRepository<Hotel, Long>{

    Hotel findByHotelName(String hotelName);
    Hotel findByHotelId(Long hotelId);

}
