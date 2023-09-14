package mini.project.HotelReservation.Repository;


import mini.project.HotelReservation.Data.Entity.User;
import mini.project.HotelReservation.Data.Enum.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
//Long : pk value
public interface UserRepository extends JpaRepository<User, Long> {
    UserStatus findStatusByEmail(String email);
    
}
