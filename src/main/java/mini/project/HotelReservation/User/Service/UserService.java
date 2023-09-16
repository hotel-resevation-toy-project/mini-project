package mini.project.HotelReservation.User.Service;


import mini.project.HotelReservation.User.Data.Dto.UserDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.User.Data.Dto.UserSignUpDto;

public interface UserService {
    //회원가입 정보를 가져와서 persist.
    void join(UserSignUpDto sud);
    Boolean checkStatus(String email);
    UserDto logIn(UserSignInDto sid);
    UserDto update(UserDto userDto);
}
