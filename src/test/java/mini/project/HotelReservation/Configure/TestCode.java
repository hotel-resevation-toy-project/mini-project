package mini.project.HotelReservation.Configure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import mini.project.HotelReservation.Configure.Seucurity.JwtTokenDecoder;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import mini.project.HotelReservation.enumerate.UserRole;
import mini.project.HotelReservation.enumerate.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
        String token = jwtTokenDecoder.createToken(1,String.valueOf(UserRole.ROLE_HOST), "1","2");

        assertNotEquals(token, null);
    }

    @Test
    @DisplayName("만들어진 토큰에서 Id 추출")
    void checkTokenToId(){
        // Controller에 Mock객체로 받아서 사용할 것
        //      (CreateToken는 void로 반환하고 세션에 저장한 메소드임)
        String token = jwtTokenDecoder.createToken(1,String.valueOf(UserRole.ROLE_HOST), "1");
        Long[] id = jwtTokenDecoder.tokenToIds(token);

        assertArrayEquals(new Long[] {1L, }, id);
    }

    @Test
    @DisplayName("만들어진 토큰에서 Id, hotelId추출")
    void checkTokenToIds(){
        // Controller에 Mock객체로 받아서 사용할 것
        //      (CreateToken는 void로 반환하고 세션에 저장한 메소드임)
        String token = jwtTokenDecoder.createToken(1,String.valueOf(UserRole.ROLE_HOST), "1","2");
        Long[] id = jwtTokenDecoder.tokenToIds(token);

        assertArrayEquals(new Long[] {1L, 2L}, id);
    }

    @Test
    @DisplayName("만들어진 토큰에서 Role을 추출")
    void checkTokenToRole(){
        // Controller에 Mock객체로 받아서 사용할 것
        //      (CreateToken는 void로 반환하고 세션에 저장한 메소드임)
        String token = jwtTokenDecoder.createToken(1,String.valueOf(UserRole.ROLE_HOST), "1","2");
        String role = jwtTokenDecoder.tokenToRole(token);

        assertEquals(String.valueOf(UserRole.ROLE_USER), role);
    }

    @Test
    @DisplayName("성공적으로 SecurityContext에 User객체가 저장되는가")
    void getUserToSecurityContext(){
        // 유저 생성
        User user = new User(
                "user", "user@play.data", "1234", "010-1111-2222",
                UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_USER
        );
        userRepository.save(user);

        // 권한부여
        User principal = userRepository.findById(1L).get();
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority(principal.getRole().toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", setAuths);
            // SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // SecurityContext에서 꺼내 현재 User 객체와 비교하기
        User checkUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assertEquals(user.getUserId(), checkUser.getUserId());
    }

    @Test
    @DisplayName("성공적으로 SecurityContext에 Role이 저장되는가")
    void getRoleToSecurityContext(){
        // 유저 생성
        User user = new User(
                "user", "user@play.data", "1234", "010-1111-2222",
                UserStatus.USER_STATUS_ACTIVE, UserRole.ROLE_USER
        );
        userRepository.save(user);

        // 권한부여
        User principal = userRepository.findById(1L).get();
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority(principal.getRole().toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", setAuths);
        // SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // SecurityContext에서 꺼낸 객체의 Role값
        assertEquals(user.getRole().toString(), SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString());
    }

    @Test
    @DisplayName("생성된 토큰 유효성 검증")
    void expiredToken(){
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject("1");
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)   // 토큰 생성 시간
                .setExpiration(new Date(now.getTime() + 1000L * 60 * 60)) // 토큰 만료 기간
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.ES256)) // 암호화 알고리즘과 SecretKey 세팅
                .compact(); // 패키징

        jwtTokenDecoder.expiredToken(token);
    }
}
