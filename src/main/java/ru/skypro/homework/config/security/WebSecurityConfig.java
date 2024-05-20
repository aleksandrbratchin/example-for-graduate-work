package ru.skypro.homework.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/webjars/**",
            "/login",
            "/register"
    };

    /**
     * InMemoryUserDetailsManager реализует UserDetailsService для обеспечения поддержки аутентификации на основе имени пользователя и пароля, которая хранится в памяти.
     */
/*    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
                UserDetails user = User.builder()
                        .username("user@gmail.com")
                        .password("password")
                        .passwordEncoder(passwordEncoder::encode)
                        .roles(Role.USER.name())
                        .build();
        return new InMemoryUserDetailsManager(user);
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorization ->
                                authorization
                                        .requestMatchers(AUTH_WHITELIST)
                                        .permitAll()
                                        .requestMatchers("/ads/**", "/users/**")
                                        .authenticated())
                .httpBasic(withDefaults())
                .cors(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}