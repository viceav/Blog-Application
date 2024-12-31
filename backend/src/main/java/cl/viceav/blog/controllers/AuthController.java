package cl.viceav.blog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.viceav.blog.security.JwtUtil;

@RestController
public class AuthController {
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserDetailsService userDetailsService;

  @PostMapping("/login")
  public Map<String, String> login(@RequestParam("username") String username,
      @RequestParam("password") String password) {
    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
          password);
      authenticationManager.authenticate(authenticationToken);
      String token = jwtUtil.createToken(userDetails);
      return Map.of("token", token);
    } catch (Exception e) {
      return Map.of("error", "Login bad credentials");
    }
  }
}
