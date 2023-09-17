package mini.project.HotelReservation.User.Repository;


import mini.project.HotelReservation.User.Data.Dto.UserReservationResponseDto;
import mini.project.HotelReservation.User.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Long : pk value
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findStatusByEmail(String email);
    Optional<UserReservationResponseDto> findReservationByUserId(Long userId);
}
