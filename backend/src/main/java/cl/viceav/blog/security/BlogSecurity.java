package cl.viceav.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class BlogSecurity {
  @Autowired
  JwtTokenFilter jwtTokenFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())// ignoringRequestMatchers("/login")
        .authorizeHttpRequests(
            (requests) -> requests.requestMatchers(HttpMethod.GET, "/blog/get/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated())
        .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }
}
