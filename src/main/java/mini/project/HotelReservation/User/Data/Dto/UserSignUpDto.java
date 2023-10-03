package mini.project.HotelReservation.User.Data.Dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import mini.project.HotelReservation.enumerate.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDto {

    private String name;
    private String email;
    private String password;
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$")
    private String phoneNumber;
    private UserRole role;
}
