package mini.project.HotelReservation.Repository;

import mini.project.HotelReservation.Data.Entity.Hotel;
import mini.project.HotelReservation.Data.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Reservation findByReserveNumber(String reserveNumber);
    List<Reservation> findAllByUser_UserId(Long userId);
    // custom repository로 해결
    Hotel findHotelByHotelName(String HotelName);
    void deleteByReserveNumber(String reserveNumber);
}
