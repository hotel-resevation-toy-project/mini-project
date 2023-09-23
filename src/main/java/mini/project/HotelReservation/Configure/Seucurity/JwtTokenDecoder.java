package mini.project.HotelReservation.Configure.Seucurity;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mini.project.HotelReservation.User.Data.Entity.User;
import mini.project.HotelReservation.User.Repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenDecoder implements TokenDecoder{

    private final UserRepository userRepository;

    // secretKey값을 디코딩한 결과를 저장하는 Key
    private Key key;
    // JWT payload에 들어갈 유효기간 (계산용)
    private final long tokenValidMillisecond = 1000L * 60 * 60;

    // 빈이 주입받은 직후 자동 실행되는 메소드, secretKey 값을 Base64로 디코딩
    @Override
    @PostConstruct
    public void init() {
        // HMAC-SHA-256 알고리즘에 적합한 256비트 이상의 키 생성
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public void createToken(String role, String... ids) {
        Claims claims = Jwts.claims();
        // 호텔 ID가 포함된 ids라면 Host 계정이므로 hotelID도 저장한다
        if(ids.length > 1){
            claims.setSubject(ids[0]+"/"+ids[1]);
        } else{ // HOST가 아니라면 유저ID만 저장한다.
            claims.setSubject(ids[0]);
        }
        // Audience(대상) 값에 role 등록
        claims.setAudience(role);
        // 유효기간 계산할 현재 시간 저장
        Date now = new Date();

        String token =Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)   // 토큰 생성 시간
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 토큰 만료 기간
                .signWith(key) // 암호화 알고리즘과 SecretKey 세팅
                .compact(); // 패키징

        // 생성된 토큰값 세션에 저장
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        req.getSession().setAttribute("Token", token);

        // 헤더에 전송
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Token " + token);
    }

    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    @Override
    public Authentication getAuthentication(String token) {
        // 토큰에서 추출한 userId를 기반으로 user객체 생성
        // 토큰에서 추출한 Role을 기반으로 시큐리티 메소드에 들어갈 Authority Collection 생성
        // List형태로 생성하는 이유는, 한 User마다 여러 역할을 가질 수 있기 때문이다
        // User A가 게시판1에서는 관리자이지만 게시판2에서는 그냥 user일 수 있음.
        User principal = userRepository.findById(tokenToIds(token)[0]).get();
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(new String[]{tokenToRole(token)}).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority(principal.getRole().toString()));

        // Filter단계에서는 매개변수값이 principal(우리로 치면 회원), 인증정보확인(값이 들어만 있으면 됨)으로
        // 권한 확인 전의 Authentication 객체를 생성하고 현재 메소드를 대기하고 있다가
        // 아래 UsernamePassword~~의 매개변수에 authorities(역할)이 포함되어 생성자가 실행되면
        // 권한을 인가받고 SecurityContextHolder에 저장되어 우리가 사용한다.
        return new UsernamePasswordAuthenticationToken(principal, "", setAuths);
    }

    @Override
    public Long[] tokenToIds(String token) {
        // 토큰을 들고와서 Id로 반환해주는 메소드
        //    토큰을 생성했을 때, User라면 userId
        //           Host라면 userId + "/" + hotelId 로 저장한 값을
        //    Long[]으로 반환함
        String info = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Arrays.stream(info.split("/")).mapToLong(Long::parseLong).boxed().toArray(Long[]::new);
    }

    @Override
    public String tokenToRole(String token) {
        // 토큰을 들고와서 role을 반환해주는 메소드
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getAudience();
    }

    @Override
    public String resolveToken(HttpServletRequest req) {
        // 세션에 있는 토큰값 추출해서 사용
        // 나중에 UserController에서 세션값 key 어떻게 저장했는지 보고 변경
        return String.valueOf(req.getSession().getAttribute("Token"));
    }

    @Override
    public boolean expiredToken(String token) {
        // 토큰 유효성 검증 수행
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("로그인 먼저 해주세요.");
//            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("########만료된 JWT 토큰입니다.");
//            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("$$$$$$$$$지원되지 않는 JWT 토큰입니다.");
//            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("%%%%%%%%%%%%JWT 토큰이 잘못되었습니다.");
//            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    @Override
    public User currentUser() {
        // 현재 유저 정보는 SecurityContextHolder에
        //    Authentication객체에 (principal, 유효한지, 역할)로 저장해놓은 상태이므로
        //          Authentication 가져오기
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Security Context에 인증 정보가 없는 상태
        if(authentication == null){
            throw new NoSuchElementException("로그인 먼저 시도해주세요.");
        }

        // principal객체로 저장된 user를 받기 위한 코드
        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        } else {
            throw new NullPointerException("로그인 먼저 시도해주세요.");
        }
    }
}