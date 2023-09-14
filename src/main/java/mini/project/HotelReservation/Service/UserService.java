package mini.project.HotelReservation.Service;

import mini.project.HotelReservation.Data.Dto.UserDto;
import mini.project.HotelReservation.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.Data.Entity.User;

public interface UserService {
    UserDto join(UserSignUpDto sud);
    User checkStatus(String email);
    UserDto logIn(UserSignInDto sid);
    UserDto update(UserDto userDto);
    UserDto deactive();
}
