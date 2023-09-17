package mini.project.HotelReservation.Configure.Seucurity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    // 토큰 디코더
    private final JwtTokenDecoder td;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 세션에서 토큰값 가져오기
        String token = td.resolveToken(request);
        // token이 Null이 아니고, 유효기간이 넘지 않았을 때
        // SecurityContextHolder에 저장
        if (StringUtils.hasText(token) && td.expiredToken(token)) {
            Authentication authentication = td.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
