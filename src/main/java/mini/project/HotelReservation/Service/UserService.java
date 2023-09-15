package mini.project.HotelReservation.Service;

import mini.project.HotelReservation.Data.Dto.UserDto;
import mini.project.HotelReservation.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.Data.Entity.User;

public interface UserService {
    //회원가입 정보를 가져와서 persist.
    void join(UserSignUpDto sud);
    Boolean checkStatus(String email);
    UserDto logIn(UserSignInDto sid);
    UserDto update(UserDto userDto);
}
