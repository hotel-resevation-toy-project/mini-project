package mini.project.HotelReservation.User.Data.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.enumerate.UserRole;

@Data
@RequiredArgsConstructor
public class UserSignUpDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    private final String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호를 정확히 입력해주세요.")
    private final String phoneNumber;

    private final UserRole role;
}
