package mini.project.HotelReservation.daisie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<hotel, Long> {

    Hotel findByHotelName(String hotelName);



}
