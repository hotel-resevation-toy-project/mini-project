package mini.project.HotelReservation.User.Repository;


import mini.project.HotelReservation.Reservation.Data.Entity.Reservation;
import mini.project.HotelReservation.User.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Long : pk value
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findStatusByEmail(String email);
    @Query("select r from User u join u.reservations r where u.userId = :userId")
    List<Reservation> findReservationsByUserId(Long userId);
}
