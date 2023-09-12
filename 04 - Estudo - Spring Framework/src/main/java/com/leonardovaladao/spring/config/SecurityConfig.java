package com.leonardovaladao.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.stereotype.Component;

import com.leonardovaladao.spring.services.UserService;

@Component
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    private SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/anime/*").authenticated()
                        .requestMatchers(HttpMethod.POST).permitAll()
                        .anyRequest().permitAll())
                // .formLogin(Customizer.withDefaults())
                .formLogin(login -> login.disable())
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                // .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .httpBasic(Customizer.withDefaults())
                // .rememberMe(Customizer.withDefaults())
                .build();
    }

    @Bean
    private UserDetailsService userDetailsService() {
        // UserDetails admin =
        // User.withUsername("admin").password(encoder.encode("123")).roles("ADMIN").build();
        // UserDetails user =
        // User.withUsername("user").password(encoder.encode("123")).roles("USER").build();

        // return new InMemoryUserDetailsManager(admin, user);

        return new UserInfo(userService);
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(this.userDetailsService());
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());

        return authenticationProvider;
    }

}
