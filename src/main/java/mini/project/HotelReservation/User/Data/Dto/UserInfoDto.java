package mini.project.HotelReservation.User.Data.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.User.Data.Entity.User;

@Data
@RequiredArgsConstructor
public class UserInfoDto {
    @NotBlank
    private final String name;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$")
    private final String phoneNumber;

    public UserInfoDto(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }

}
