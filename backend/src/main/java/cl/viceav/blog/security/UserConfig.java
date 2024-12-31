package cl.viceav.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {
  @Autowired
  PasswordEncoder passwordEncoder;

  @Bean
  public UserDetailsService userDetailsService() {
    String password = "testing";
    UserDetails user = User.withUsername("viceav").password(passwordEncoder.encode(password)).build();

    return new InMemoryUserDetailsManager(user);
  }
}
