package mini.project.HotelReservation.User.Data.Dto.response;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.Configure.Seucurity.JwtTokenDecoder;
import mini.project.HotelReservation.User.Data.Entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Data
@RequiredArgsConstructor
public class UserSignInResponseDto {
    private final String token;
    private final String userName;
    private final String email;
    private final String phoneNumber;

    public UserSignInResponseDto(Authentication authentication, JwtTokenDecoder token){
        //토큰값 가져오기
        String tokenValue = token.resolveToken(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

        //토큰값 사용하여 사용자 정보를 가져오기
        Optional<User> user = token.currentUser();
        if(user.isPresent()) {
            this.token = tokenValue;
            this.userName = user.get().getName();
            this.email = user.get().getEmail();
            this.phoneNumber = user.get().getPhoneNumber();
        }else{
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }
    }
}
