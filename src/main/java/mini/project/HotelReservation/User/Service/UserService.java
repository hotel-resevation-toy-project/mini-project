package mini.project.HotelReservation.User.Service;


import mini.project.HotelReservation.User.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.User.Data.Dto.UserInfoDto;
import mini.project.HotelReservation.User.Data.Dto.UserReservationDto;
import mini.project.HotelReservation.User.Data.Entity.User;

import java.util.List;

public interface UserService {
    void join(UserSignUpDto sud);
    Boolean checkStatus(String email);
    void logIn(UserSignInDto sid);
    void updateInfo(UserInfoDto userInfoDto);
    List<UserReservationDto> reservationList(Long userId);
    void deactive(String email, UserSignInDto usd);
    User loadUserByUserId(Long id);
}
