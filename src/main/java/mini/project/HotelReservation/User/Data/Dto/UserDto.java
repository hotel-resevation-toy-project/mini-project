package mini.project.HotelReservation.User.Data.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mini.project.HotelReservation.User.Data.Enum.UserRole;
import mini.project.HotelReservation.User.Data.Enum.UserStatus;

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