package mini.project.HotelReservation.Reservation.Repository;

import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long>, ReservationJoinHotelRepository{

    Reservation findByReserveNumber(String reserveNumber);
    List<Reservation> findAllByUser_UserId(Long userId);

    @Override
    Hotel findByHotelName(String hotelName);

    // 호텔의 룸 타입별 현재 예약된 예약 목록
    @Override
    List<Reservation> findReservationListByHotelNameAndRoomType(String hotelName, RoomType roomType);

    // 호텔의 룸 타입별 현재 예약된 방 개수
    @Override
    Long findReservationCountByHotelNameAndRoomType(String hotelName, RoomType roomType);

    @Override
    List<Reservation> findByHotelId(Long id);

    void deleteByReserveNumber(String reserveNumber);
}
