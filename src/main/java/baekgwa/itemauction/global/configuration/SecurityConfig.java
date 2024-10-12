package baekgwa.itemauction.global.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("TRADER")
                .role("TRADER").implies("BUYER")
                .role("BUYER").implies("NONE")
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/users/add/**", "/error/**", "/loginProc", "/css/**", "/favicon.ico/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        .requestMatchers("/manage/**").hasAnyRole("TRADER")
                        .requestMatchers("/items/**").hasAnyRole("BUYER")
                        .requestMatchers("/mypage/**").hasAnyRole("NONE")
                        .anyRequest().authenticated());

        http
                .formLogin(auth -> auth
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
//                        .successHandler(((request, response, authentication) -> {
//                            response.sendRedirect("/login/aram");
//                        })) //로그인 성공시 추가 로직이 필요한 경우 다음과 같이 체이닝 하여 사용할 것.
                        .defaultSuccessUrl("/", true)
                        .permitAll());

        http
                .sessionManagement(auth -> auth
                        .maximumSessions(1) //로그인 최대 허용치
                        .maxSessionsPreventsLogin(false)); //로그인 허용치 초과시 새로운 로그인 차단

        http
                .sessionManagement(auth -> auth
                        .sessionFixation().changeSessionId());

        http
                .logout(auth -> auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true));

        return http.build();
    }
}