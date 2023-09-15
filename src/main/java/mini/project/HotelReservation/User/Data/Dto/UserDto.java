package mini.project.HotelReservation.User.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;

@Data
@AllArgsConstructor
public class UserDto {
    private final String name;

    private final String email;

    private final String password;

    private final String phoneNumber;

    private final UserStatus status;

    private final UserRole role;
}
