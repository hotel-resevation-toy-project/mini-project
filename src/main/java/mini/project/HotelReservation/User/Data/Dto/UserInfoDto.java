package mini.project.HotelReservation.User.Data.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import mini.project.HotelReservation.User.Data.Entity.User;

@Data
public class UserInfoDto {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$")
    private String phoneNumber;

    public UserInfoDto(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }
}
