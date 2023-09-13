package mini.project.HotelReservation.user.service;

import mini.project.HotelReservation.user.data.UserDto;
import mini.project.HotelReservation.user.data.UserSignInDto;
import mini.project.HotelReservation.user.data.UserSignUpDto;
import mini.project.HotelReservation.user.entity.User;

public interface UserService {
    UserDto join(UserSignUpDto sud);
    User checkStatus(String email);
    UserDto logIn(UserSignInDto sid);
    UserDto update(UserDto userDto);
    UserDto deactive();
}
