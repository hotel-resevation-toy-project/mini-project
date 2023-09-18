package mini.project.HotelReservation.User.Service;


import mini.project.HotelReservation.User.Data.Dto.request.UserDeactiveDto;
import mini.project.HotelReservation.User.Data.Dto.request.UserSignInRequestDto;
import mini.project.HotelReservation.User.Data.Dto.request.UserSignUpDto;
import mini.project.HotelReservation.User.Data.Dto.response.UserInfoDto;
import mini.project.HotelReservation.User.Data.Dto.response.UserReservationDto;
import mini.project.HotelReservation.User.Data.Entity.User;

import java.util.List;

public interface UserService {
    void join(UserSignUpDto sud);
    Boolean checkStatus(String email);
    UserInfoDto logIn(UserSignInRequestDto sid);
    void updateInfo(UserInfoDto userInfoDto);
    List<UserReservationDto> reservationList(Long userId);
    void deactive(String email, UserDeactiveDto udd);
    User loadUserByUserId(Long id);
}
