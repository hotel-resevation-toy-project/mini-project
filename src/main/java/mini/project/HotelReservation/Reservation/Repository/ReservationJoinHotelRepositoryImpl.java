package mini.project.HotelReservation.Reservation.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.enumerate.RoomType;
import org.hibernate.usertype.UserTypeLegacyBridge;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationJoinHotelRepositoryImpl implements ReservationJoinHotelRepository{


    private final EntityManager em;
    @Override
    public Hotel findByHotelName(String hotelName) {
        return em.createQuery("SELECT h FROM Hotel h " +
                        "WHERE h.hotelName = :hotelName", Hotel.class)
                .setParameter("hotelName",hotelName)
                .getSingleResult();
    }

    @Override
    public List<Reservation> findByHotelNameAndRoomType(String hotelName, RoomType roomType) {
        return em.createQuery("select r from Reservation r " +
                        "join r.room ro " +
                        "join ro.hotel h " +
                        "where ro.roomType = :roomType and h.hotelName = :hotelName", Reservation.class)
                .setParameter("roomType", roomType)
                .setParameter("hotelName", hotelName)
                .getResultList();
    }

    @Override
    public List<Reservation> findByHotelId(Long id) {
        return em.createQuery("select r from Reservation r " +
                        "join r.room ro " +
                        "join ro.hotel h " +
                        "where h.hotelId = :hotelId ", Reservation.class)
                        .setParameter("hotelId",id)
                        .getResultList();
    }
}
