package mini.project.HotelReservation.Configure;

import jakarta.servlet.http.HttpServletRequest;
import mini.project.HotelReservation.Configure.Seucurity.JwtTokenDecoder;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import mockit.Mocked;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SecurityTokenTest {
    private final UserRepository userRepository;
    private final JwtTokenDecoder jwtTokenDecoder;

    @Mocked
    HttpServletRequest mockRequest;

    @Autowired
    public SecurityTokenTest(UserRepository userRepository, JwtTokenDecoder jwtTokenDecoder) {
        this.userRepository = userRepository;
        this.jwtTokenDecoder = jwtTokenDecoder;
    }

    @BeforeEach
    public void init(){
        userRepository.deleteAll();
        jwtTokenDecoder.init();
        // 유저 생성
        User user = new User(
                "user", "user@play.data", "1234", "010-1111-2222",
                UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_USER
        );
        userRepository.save(user);
        mockRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    @AfterEach
    public void reset(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("토큰_생성")
    void creatToken(){
        // Controller에 Mock객체로 받아서 사용할 것
        //      (CreateToken는 void로 반환하고 세션에 저장한 메소드임)
        jwtTokenDecoder.createToken(String.valueOf(UserRole.ROLE_USER), "1");
        String token = jwtTokenDecoder.resolveToken(mockRequest);
        assertNotEquals(token, null);
    }

    @Test
    @DisplayName("만들어진_토큰에서_Id_추출")
    void checkTokenToId(){
        // Controller에 Mock객체로 받아서 사용할 것
        //      (CreateToken는 void로 반환하고 세션에 저장한 메소드임)
        jwtTokenDecoder.createToken(String.valueOf(UserRole.ROLE_USER), "1");
        String token = jwtTokenDecoder.resolveToken(mockRequest);
        Long[] id = jwtTokenDecoder.tokenToIds(token);

        assertArrayEquals(new Long[] {1L, }, id);
    }

    @Test
    @DisplayName("만들어진_토큰에서_Id,hotelId추출")
    void checkTokenToIds(){
        // Controller에 Mock객체로 받아서 사용할 것
        //      (CreateToken는 void로 반환하고 세션에 저장한 메소드임)
        jwtTokenDecoder.createToken(String.valueOf(UserRole.ROLE_USER), "1");
        String token = jwtTokenDecoder.resolveToken(mockRequest);
        Long[] id = jwtTokenDecoder.tokenToIds(token);

        assertArrayEquals(new Long[] {1L, }, id);
    }

    @Test
    @DisplayName("Token에서_Role_추출")
    void checkTokenToRole(){
        // Controller에 Mock객체로 받아서 사용할 것
        //      (CreateToken는 void로 반환하고 세션에 저장한 메소드임)
        jwtTokenDecoder.createToken(String.valueOf(UserRole.ROLE_USER), "1");
        String token = jwtTokenDecoder.resolveToken(mockRequest);
        String role = jwtTokenDecoder.tokenToRole(token);

        assertEquals(String.valueOf(UserRole.ROLE_USER), role);
    }

    @Test
    @DisplayName("SecurityContext에_User저장")
    void getUserToSecurityContext(){
        jwtTokenDecoder.createToken(String.valueOf(UserRole.ROLE_USER), String.valueOf(userRepository.findAll().get(0).getUserId()));
        String token = jwtTokenDecoder.resolveToken(mockRequest);
        // SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(jwtTokenDecoder.getAuthentication(token));

        // SecurityContext에서 꺼내 현재 User 객체와 비교하기
        User checkUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assertEquals(jwtTokenDecoder.currentUser().getUserId(), checkUser.getUserId());
    }

    @Test
    @DisplayName("SecurityContext에_Authentication_저장")
    void getRoleToSecurityContext(){
        // 권한부여
        jwtTokenDecoder.createToken(String.valueOf(UserRole.ROLE_USER), String.valueOf(userRepository.findAll().get(0).getUserId()));
        String token = jwtTokenDecoder.resolveToken(mockRequest);
        // SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(jwtTokenDecoder.getAuthentication(token));

        // SecurityContext에서 꺼낸 객체의 Role값
        assertEquals(userRepository.findAll().get(0).getRole().toString(), jwtTokenDecoder.currentUser().getRole().toString());
    }

//    @Test
//    @DisplayName("생성된 토큰 유효성 검증")
//    void expiredToken(){
//        Date now = new Date();
//        Claims claims = Jwts.claims().setSubject("1");
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)   // 토큰 생성 시간
//                .setExpiration(new Date(now.getTime() + 1000L * 60 * 60)) // 토큰 만료 기간
//                .signWith(Keys.secretKeyFor(SignatureAlgorithm.ES256)) // 암호화 알고리즘과 SecretKey 세팅
//                .compact(); // 패키징
//
//        jwtTokenDecoder.expiredToken(token);
//    }

    @Test
    @DisplayName("Token_Session에_저장후_받기")
    void tokenSetToSession() throws IOException {
        // 리퀘스트 선언 후
        jwtTokenDecoder.createToken(String.valueOf(UserRole.ROLE_HOST), String.valueOf(userRepository.findAll().get(0).getUserId()));
        String token = jwtTokenDecoder.resolveToken(mockRequest);

        assertEquals(jwtTokenDecoder.tokenToIds(token)[0], userRepository.findAll().get(0).getUserId());
    }
}