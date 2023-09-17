package mini.project.HotelReservation.User.Service;


import mini.project.HotelReservation.User.Data.Dto.UserDeactiveDto;
import mini.project.HotelReservation.User.Data.Dto.UserDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;

public interface UserService {
    void join(UserSignUpDto sud);
    Boolean checkStatus(String email);
    UserDto logIn(UserSignInDto sid);
    UserDto update(UserDto userDto);
    void deactive(String email, UserDeactiveDto udd);
}
