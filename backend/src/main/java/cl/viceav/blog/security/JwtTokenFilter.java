package cl.viceav.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getTokenFromRequest(request);
    String username = null;
    if (token != null) {
      try {
        username = jwtUtil.getUsername(token);
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        try {
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          if (jwtUtil.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          }
        } catch (Exception e) {
          System.out.println("Invalid token");
        }
      }
    }
    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearer = request.getHeader("Authorization");

    if (bearer != null && bearer.startsWith("Bearer ")) {
      return bearer.substring(7);
    }

    return null;
  }
}
