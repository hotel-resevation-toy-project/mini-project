package mini.project.HotelReservation.Configure.Seucurity;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public class JwtTokenDecoder implements TokenDecoder{
    @Override
    public void init() {

    }

    @Override
    public String createToken(String role, String... ids) {
        return null;
    }

    @Override
    public Authentication getAuthentication(String token) {
        return null;
    }

    @Override
    public Long tokenToIds(String token) {
        return null;
    }

    @Override
    public String resolveToken(HttpServletRequest req) {
        return null;
    }

    @Override
    public boolean expiredToken(String token) {
        return false;
    }
}
