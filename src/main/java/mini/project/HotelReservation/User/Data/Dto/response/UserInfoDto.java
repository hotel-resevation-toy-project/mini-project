package mini.project.HotelReservation.User.Data.Dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.User.Data.Entity.User;

@Data
@RequiredArgsConstructor
public class UserInfoDto {
    private final String name;

    private final String email;

    private final String password;

    private final String phoneNumber;

    public UserInfoDto(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
    }
}
