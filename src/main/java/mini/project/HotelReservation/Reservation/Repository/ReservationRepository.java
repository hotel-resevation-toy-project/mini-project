package mini.project.HotelReservation.Reservation.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    //상세 예약
    Reservation findByReserveNumber(String reserveNumber);
    //유저측 예약 목록
    List<Reservation> findAllByUser_UserId(Long userId);
    //호텔측 예약 목록
    List<Reservation> findAllByHotel_HotelId(Long hotelId);
    // 호텔의 룸 타입별 현재 예약된 방 개수 - 예약 순서 만들기용
    @Query("SELECT count(*) FROM Reservation r " +
            "join Hotel h " +
            "where r.hotel.hotelId = h.hotelId " +
            "and h.hotelName = :hotelName " +
            "and r.roomType = :roomType")
    Long findCountByHotelAndRoom(String hotelName, RoomType roomType);
    void deleteByReserveNumber(String reserveNumber);
}
