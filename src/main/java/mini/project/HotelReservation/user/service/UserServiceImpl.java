package mini.project.HotelReservation.user.service;

import mini.project.HotelReservation.user.data.UserDto;
import mini.project.HotelReservation.user.data.UserSignInDto;
import mini.project.HotelReservation.user.data.UserSignUpDto;
import mini.project.HotelReservation.user.entity.User;
import mini.project.HotelReservation.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    TokenDecoder td;
    @Override
    public UserDto join(UserSignUpDto sud) {
        return null;
    }

    @Override
    public User checkStatus(String email) {
        return null;
    }

    @Override
    public UserDto logIn(UserSignInDto sid) {
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto deactive() {
        return null;
    }

}
