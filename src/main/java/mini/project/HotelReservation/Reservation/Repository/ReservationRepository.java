package mini.project.HotelReservation.Reservation.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long>, ReservationJoinHotelRepository{

    Reservation findByReserveNumber(String reserveNumber);
    List<Reservation> findAllByUser_UserId(Long userId);

    @Override
    Hotel findByHotelName(String hotelName);

    void deleteByReserveNumber(String reserveNumber);
}