package mini.project.HotelReservation.Service;

import mini.project.HotelReservation.Data.Dto.UserDto;
import mini.project.HotelReservation.Data.Dto.UserSignInDto;
import mini.project.HotelReservation.Data.Dto.UserSignUpDto;
import mini.project.HotelReservation.Data.Entity.User;
import mini.project.HotelReservation.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
//    TokenDecoder td;
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
