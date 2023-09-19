package mini.project.HotelReservation.Configure;

import mini.project.HotelReservation.Configure.Seucurity.JwtTokenDecoder;
import mini.project.HotelReservation.User.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class TestCode {
    private final UserRepository userRepository;
    private final JwtTokenDecoder jwtTokenDecoder;

    @Autowired
    public TestCode(UserRepository userRepository, JwtTokenDecoder jwtTokenDecoder) {
        this.userRepository = userRepository;
        this.jwtTokenDecoder = jwtTokenDecoder;
    }

    @BeforeEach
    public void init(){
        userRepository.deleteAll();
        jwtTokenDecoder.init();
    }

    @Test
    @DisplayName("토큰이 생성됨")
    void creatToken(){
        // Controller에 Mock객체로 받아서 사용할 것
        //      (CreateToken는 void로 반환하고 세션에 저장한 메소드임)
        String token = jwtTokenDecoder.createToken(1,"user", "1","2");

        assertNotEquals(token, null);
    }

//    @Test
//    @DisplayName("토큰의 payload값이 조회된다.")
//    void getPayLoadByValidToken(){
//        String token = jwtTokenDecoder.createToken("user", "1","2");
//        Long[] ids = jwtTokenDecoder.tokenToIds(token);
//        String role = jwtTokenDecoder.tokenToRole(token);
//        assertEquals(ids, new Long[]{1L, 2L});
//        assertEquals(role, "user");
//    }
//
//    @Test
//    @DisplayName("유효하지 않은 토큰 형식의 토큰으로 payload를 조회할 경우 예외를 발생시킨다.")
//    void getPayloadByInvalidToken() {
//        assertThatExceptionOfType(TokenInvalidFormException.class)
//                .isThrownBy(() -> jwtTokenDecoder.getPayload(null));
//    }
}
