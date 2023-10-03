package mini.project.HotelReservation.User.Repository;


import mini.project.HotelReservation.User.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.userId = : id")
    User findByTokenId(@Param("id") Long id);
}
