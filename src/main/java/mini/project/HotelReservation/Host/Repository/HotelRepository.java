package mini.project.HotelReservation.Host.Repository;

import mini.project.HotelReservation.Host.Data.Dto.HotelReservationResponseDto;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Hotel findByHotelName(String hotelName);
    Hotel findByHotelId(Long hotelId);
    Optional<HotelReservationResponseDto> findReservationByHotelIdy(Long hotelId);
}
