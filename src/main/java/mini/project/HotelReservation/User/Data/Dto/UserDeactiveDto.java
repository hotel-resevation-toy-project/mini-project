package mini.project.HotelReservation.User.Data.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDeactiveDto {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
