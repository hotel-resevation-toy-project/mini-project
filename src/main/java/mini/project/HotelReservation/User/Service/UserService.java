package mini.project.HotelReservation.User.Service;


import mini.project.HotelReservation.User.Data.Dto.*;
import mini.project.HotelReservation.User.Data.Entity.User;

import java.util.List;

public interface UserService {
    void join(UserSignUpDto sud);
    Boolean checkStatus(String email);
    void logIn(UserSignInDto sid);
    UserDto update(UserDto userDto);
    List<UserReservationResponseDto> reservationList(Long userId);
    void deactive(String email, UserDeactiveDto udd);
    User loadUserByUserId(Long id);
}
