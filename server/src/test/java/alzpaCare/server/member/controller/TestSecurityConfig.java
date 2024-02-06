package alzpaCare.server.member.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@TestConfiguration
public class TestSecurityConfig extends SecurityConfigurerAdapter { // JWT 인증과정을 무시하기 위한 테스트용 시큐리티 config
    @Bean
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf((csrfConfig) ->
                        csrfConfig.disable())

                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/","/login/**","/join/**").permitAll()
                                .requestMatchers("/find/id/**","/find/pw/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                                .requestMatchers("/member/**").hasRole("USER")
                                .requestMatchers("/product/**").hasRole("USER")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                );
    }


}
