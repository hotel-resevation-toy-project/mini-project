package mini.project.HotelReservation.User.Service;


import mini.project.HotelReservation.Configure.Seucurity.TokenDecoder;
import mini.project.HotelReservation.User.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.User.Data.Dto.UserInfoDto;
import mini.project.HotelReservation.User.Data.Dto.UserReservationDto;
import mini.project.HotelReservation.User.Data.Entity.User;

import java.util.List;

public interface UserService {
    void join(UserSignUpDto sud);
    Boolean checkStatus(User user);
    void logIn(UserSignInDto sid);
    UserInfoDto getUserInfo();
    void updateInfo(UserInfoDto userInfoDto);
    List<UserReservationDto> reservationList();
    void deactive(String password);
    User loadUserByUserId(Long id);
}
