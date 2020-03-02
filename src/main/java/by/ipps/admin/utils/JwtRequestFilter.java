package by.ipps.admin.utils;

import by.ipps.admin.entity.User;
import by.ipps.admin.service.JwtUserDetailsService;
import by.ipps.admin.utils.resttemplate.UserRestTemplate;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUserDetailsService jwtUserDetailsService;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserRestTemplate restRequestToDao;
  @Autowired private AuthenticationManager authenticationManager;

  public JwtRequestFilter(
      JwtUserDetailsService jwtUserDetailsService,
      JwtTokenUtil jwtTokenUtil,
      UserRestTemplate restRequestToDao) {
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
    this.restRequestToDao = restRequestToDao;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader("Authorization");
    System.out.println(requestTokenHeader);
    String username = null;
    String jwtToken = null;
    // JWT Token is in the form "Bearer token". Remove Bearer word and get
    // only the Token
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        System.out.println("JWT Token has expired");
      }
      if (username != null) {
        User user = restRequestToDao.getUserByLogin(username);
        if (jwtTokenUtil.validateToken(jwtToken, user)) {
          UserDetails userDetails =
              new org.springframework.security.core.userdetails.User(
                  user.getLogin(),
                  user.getHashPassword(),
                  user.isEnabled(),
                  true,
                  true,
                  !user.isBlock(),
                  getAuthorities(user.getRoles()));
          if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
            usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
          }
        }
      }
    } else {
      logger.warn("JWT Token does not begin with Bearer String");
    }
    chain.doFilter(request, response);
  }

  private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {

    return getGrantedAuthorities(getPrivileges(roles));
  }

  private List<String> getPrivileges(List<String> roles) {
    List<String> privileges = new ArrayList<>();
    for (String role : roles) {
      privileges.add(role);
    }
    return privileges;
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }
    return authorities;
  }
}
