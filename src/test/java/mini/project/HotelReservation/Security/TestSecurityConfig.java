package mini.project.HotelReservation.Security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@TestConfiguration
public class TestSecurityConfig{ // JWT 인증과정을 무시하기 위한 테스트용 시큐리티 config

    @Bean
    public void configure(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}