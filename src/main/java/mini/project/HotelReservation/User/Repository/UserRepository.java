package mini.project.HotelReservation.User.Repository;


import mini.project.HotelReservation.User.Data.Dto.UserInfoDto;
import mini.project.HotelReservation.User.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.rmi.server.UID;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.userId = :id")
    User findByTokenId(@Param("id") Long id);

    @Query("SELECT new mini.project.HotelReservation.User.Data.Dto.UserInfoDto(u.name, u.email, u.phoneNumber)" +
            "FROM User u " +
            "WHERE u.userId = :id")
    UserInfoDto findDtoByTokenId(@Param("id") Long id);

    // 호스트 유저 아이디 -> 호텔 아이디
    @Query("SELECT u.hotel.hotelId " +
            "FROM User u " +
            "WHERE u.userId =:id")
    Long findHotelIdByTokenId(@Param("id") Long id);

    @Query("SELECT h.hotelName " +
            "FROM User u " +
            "JOIN u.hotel h " +
            "WHERE u.userId =:id")
    String  findHotelNameByTokenId(@Param("id") Long id);
}
