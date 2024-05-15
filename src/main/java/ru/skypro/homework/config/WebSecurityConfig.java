package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.skypro.homework.dto.Role;

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
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user =
                User.builder()
                        .username("user@gmail.com")
                        .password("password")
                        .passwordEncoder(passwordEncoder::encode)
                        .roles(Role.USER.name())
                        .build();
        return new InMemoryUserDetailsManager(user);
    }

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
                //.cors(withDefaults());
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource());
        return http.build();
    }

    // Настройка бина CorsConfigurationSource
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // Разрешаем запросы от любого источника
        configuration.addAllowedMethod("*"); // Разрешаем использование любых HTTP-методов
        configuration.addAllowedHeader("*"); // Разрешаем использование любых заголовков
        configuration.setAllowCredentials(true); // Разрешаем отправку аутентификационных данных (например, cookies) с запросом
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Применяем конфигурацию к всем URL
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
