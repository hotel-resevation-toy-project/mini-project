package mini.project.HotelReservation.Configure.Seucurity;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 체인필터에 등록
public class SecurityConfiguration {
    private final JwtTokenDecoder td;

    // 로직을 타지않는 페이지들을 보안 로직에서 권한 검사를 하지않게 하는 시큐리티 메서드
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Spring Security를 적용하지 않을 리소스 설정
        return (web) -> web.ignoring().requestMatchers("/user/in", "/user/new", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // 토큰을 활용할 예정이니 csrf(Cross Site Request Forgery Attack : 사이트 간 요청 위조) 설정을 꺼놓기로 함
                // JWT에는 사용자 인증 정보가 이미 포함되어 있으므로 별도의 CSRF 토큰을 사용하지 않아도 보안이 유지가 가능하고
                // 충돌의 우려가 있음.
                .httpBasic(AbstractHttpConfigurer::disable)
                //  Spring Security를 사용하는 경우, HTTP 기본 인증을 비활성화하고 다른 인증 메커니즘을 사용하기 위해서
                // http 기본 인증을 꺼놓음.
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 세션을 유지하여 SessionId를 확인할 필요없이 요청 시에 토큰을 받아서 사용하면 되므로 세션을 유지할 이유가 없다.
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers("/host", "/host/**")
                                .hasRole("HOST")
                                .requestMatchers("/user", "/user/**", "/reservation/**")
                                .hasAnyRole("HOST", "USER")
                                .anyRequest().authenticated()   // 그 외 인증없이 접근 X
                )
//                .exceptionHandling(
//                        (exception)->exception.accessDeniedHandler(accessDeniedHandler)
//                )
                .formLogin((login) ->
                                login.loginPage("/user/in")
                                        .loginProcessingUrl("/user/in")
                                        .failureUrl("/")
                                        .defaultSuccessUrl("/")
//                                .failureHandler()
//                                .successHandler()
                )
                .logout((logout) ->
                                logout.logoutUrl("/logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
                                        .invalidateHttpSession(true) // 로그아웃시 세션 밀어버리기
                                        .logoutSuccessUrl("/user/in") // 로그아웃 성공 후 이동페이지 (로그인 화면)
//                                .deleteCookies("JSESSIONID", "remember-me") // 로그아웃 후 쿠키 삭제
                )
                // 커스텀 필터 추가 : 요청이 시작되기 전에 만들어놓은 JwtTokenFilter를 사용할 필터로 설정
                .addFilterBefore(new JwtTokenFilter(td), UsernamePasswordAuthenticationFilter.class);
        return http.build();    // 설정한 http를 생성
    }

}
