package mini.project.HotelReservation.Configure.Seucurity;

import jakarta.servlet.http.HttpServletRequest;
import mini.project.HotelReservation.User.Data.Entity.User;
import org.springframework.security.core.Authentication;


public interface TokenDecoder {
    void init();
    // ids -> userId, hotelId
    void createToken(String role, String... ids);
    Authentication getAuthentication(String token);
    // 추출한 토큰에서 userId, hotelId 가져오기
    Long[] tokenToIds(String token);
    // 추출한 토큰에서 role 가져오기
    String tokenToRole(String token);
    // 토큰 추출(우리의 경우 세션에서)
    String resolveToken(HttpServletRequest req);
    // 토큰 유효기간 검사
    boolean expiredToken(String token);
    // 현재 로그인한 사용자 정보 가져오기
    User currentUser();
}
