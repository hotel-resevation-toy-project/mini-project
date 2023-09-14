package mini.project.HotelReservation.reserve.repository;

import mini.project.HotelReservation.reserve.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve,Long> {

    Reserve findByReserveNumber(String reserveNumber);
    List<Reserve> findAllByUser_UserId(Long userId);
    List<Reserve> findAllByHotel_HotelId(Long hotelId);
    void deleteByReserveNumber(String reserveNumber);
}
