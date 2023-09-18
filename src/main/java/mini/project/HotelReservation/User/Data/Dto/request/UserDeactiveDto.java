package mini.project.HotelReservation.User.Data.Dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDeactiveDto {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;
}
