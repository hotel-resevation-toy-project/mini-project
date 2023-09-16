package mini.project.HotelReservation.Reservation.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Host.Data.Entity.Hotel;
import org.springframework.stereotype.Repository;

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
}
