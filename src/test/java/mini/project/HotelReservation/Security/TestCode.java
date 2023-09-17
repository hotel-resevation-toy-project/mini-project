package mini.project.HotelReservation.Security;

import mini.project.HotelReservation.Configure.Seucurity.JwtTokenDecoder;
import mini.project.HotelReservation.User.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
//@Import(TestSecurityConfig.class) // JWT 인증과정을 무시하기 위해 사용
public class TestCode {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenDecoder jwtTokenDecoder;

    @BeforeEach
    public void init(){
        userRepository.deleteAll();
        jwtTokenDecoder.init();
    }

    @Test
    @DisplayName("토큰이 생성됨")
    void creatToken(){
        final String token = jwtTokenDecoder.createToken("user", "1","2");
        assertNotEquals(token, null);
    }

    @Test
    @DisplayName("토큰의 payload값이 조회된다.")
    void getPayLoadByValidToken(){
        String token = jwtTokenDecoder.createToken("user", "1","2");
        Long[] ids = jwtTokenDecoder.tokenToIds(token);
        String role = jwtTokenDecoder.tokenToRole(token);
        assertEquals(ids, new Long[]{1L, 2L});
        assertEquals(role, "user");
    }
}
