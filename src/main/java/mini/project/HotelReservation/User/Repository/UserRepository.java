package mini.project.HotelReservation.User.Repository;


import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.enumerate.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
