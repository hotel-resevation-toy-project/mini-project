package mini.project.HotelReservation.user.repository;


import mini.project.HotelReservation.user.entity.User;
import mini.project.HotelReservation.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
//Long : pk value
public interface UserRepository extends JpaRepository<User, Long> {
    UserStatus findStatusByEmail(String email);
    
}
