package mini.project.HotelReservation.Reservation.Repository;

import mini.project.HotelReservation.Host.Data.Dto.HotelReservationDto;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.User.Data.Dto.UserReservationDto;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.RoomType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    //상세 예약
    Reservation findByReserveNumber(String reserveNumber);
    //유저측 예약 목록

    //호텔측 예약 목록
    @Query("SELECT new mini.project.HotelReservation.Host.Data.Dto.HotelReservationDto(r.reserveNumber, u.name , u.phoneNumber) " +
            "FROM Reservation r " +
            "JOIN r.user u " +
            "JOIN r.hotel h " +
            "WHERE r.hotel.hotelId =:hotelId")
    List<HotelReservationDto> findDtosByHotelId(@Param("hotelId") Long hotelId);


    @Query("SELECT new mini.project.HotelReservation.User.Data.Dto.UserReservationDto(r.reserveNumber, h.hotelName, r.checkInDate, r.checkOutDate)" +
            "FROM Reservation r " +
            "JOIN r.hotel h " +
            "JOIN r.user u " +
            "WHERE r.user.userId = :id")
    List<UserReservationDto> findDtosByUserId(@Param("id") Long id);
    // 호텔의 룸 타입별 현재 예약된 방 개수 - 예약 순서 만들기용
    @Query("SELECT count(*) FROM Reservation r " +
            "WHERE r.hotel.hotelName = :hotelName " +
            "AND r.roomType = :roomType")
    Long findCountByHotelNameAndRoom(@Param("hotelName")String hotelName,
                                     @Param("roomType")RoomType roomType);

    //test용
    List<Reservation> findAllByHotel_HotelId(Long hotelId);
    List<Reservation> findAllByUser_UserId(Long userId);

}
