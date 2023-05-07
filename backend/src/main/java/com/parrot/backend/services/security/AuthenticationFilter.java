package com.parrot.backend.services.security;

import com.parrot.backend.services.user.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private IJwtService jwtService;
  @Autowired
  private IUserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    if (request.getServletPath().contains("/api/v1/user/create")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (request.getServletPath().contains("/api/v1/authentication/login")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (request.getServletPath().contains("swagger") || request.getServletPath().contains("docs")) {
      filterChain.doFilter(request, response);
      return;
    }

    var token = request.getHeader("Authorization");
    var userId = request.getHeader("RequestedBy");

    if(token == null || userId == null || !token.startsWith("Bearer ")) {
      response.getWriter().write("User not authenticated!");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    boolean isValidToken = false;

    try {
      isValidToken = jwtService.isValidToken(token.substring(7), userId);
    } catch (Exception e) {
      response.getWriter().write(e.getMessage());
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    if(isValidToken) {
      try {
        var user = userService.getUserById(UUID.fromString(userId));
        var authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        response.getWriter().write(e.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return;
      }
    } else {
      response.getWriter().write("Invalid token!");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    filterChain.doFilter(request, response);
  }
}
