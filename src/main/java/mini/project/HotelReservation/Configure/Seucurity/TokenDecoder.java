package mini.project.HotelReservation.Configure.Seucurity;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public interface TokenDecoder {
    void init();
    // ids -> userId, hotelId
    String createToken(String role, String... ids);
    Authentication getAuthentication(String token);
    // 추출한 토큰에서 userId, hotelId 디코딩
    Long tokenToIds(String token);
    // 토큰 추출(우리의 경우 세션에서)
    String resolveToken(HttpServletRequest req);
    // 토큰 유효기간 검사
    boolean expiredToken(String token);
}
