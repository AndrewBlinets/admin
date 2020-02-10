package by.ipps.admin.controller;

import by.ipps.admin.entity.JwtRequest;
import by.ipps.admin.entity.JwtResponse;
import by.ipps.admin.exception.AuthException;
import by.ipps.admin.utils.JwtTokenUtil;
import by.ipps.admin.utils.RestRequestToDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final RestRequestToDao restRequestToDao;

  public JwtAuthenticationController(
      AuthenticationManager authenticationManager,
      JwtTokenUtil jwtTokenUtil,
      RestRequestToDao restRequestToDao) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.restRequestToDao = restRequestToDao;
  }

  @PostMapping(value = "/authenticate")
  public ResponseEntity<JwtResponse> createAuthenticationToken(
      @RequestBody JwtRequest authenticationRequest) {
    int a = 0;
    a++;
    a++;
    try {
      authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    } catch (AuthException e) {
      return new ResponseEntity<>(new JwtResponse(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    return ResponseEntity.ok(
        new JwtResponse(
            jwtTokenUtil.generateToken(
                restRequestToDao.getUserByLogin(authenticationRequest.getUsername())),
            "Успешно авторизован!"));
  }

  private void authenticate(String username, String password) throws AuthException {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (LockedException e) {
      throw new AuthException("Пользователь заблокирован");
    } catch (DisabledException e) {
      throw new AuthException("Пользователь отключен");
    } catch (Exception e) {
      throw new AuthException("Неверный логин или пароль!");
    }
  }
}
