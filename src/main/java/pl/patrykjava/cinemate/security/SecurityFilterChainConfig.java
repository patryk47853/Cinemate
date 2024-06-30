package pl.patrykjava.cinemate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.patrykjava.cinemate.jwt.JWTAuthenticationFilter;

@SuppressWarnings("removal")
@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider, JWTAuthenticationFilter jwtAuthenticationFilter, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/members", "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/movies", "/movies/all").permitAll()
                .requestMatchers(HttpMethod.POST, "/comments/add").permitAll()
                .requestMatchers(HttpMethod.GET, "/members", "/members/profile/").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/members/{memberId}").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/comments/movie/{movieId}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/members/{memberId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/members/{memberId}/favorites/{movieId}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/members/{memberId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/members/{memberId}/favorites/{movieId}").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);

        return http.build();
    }
}