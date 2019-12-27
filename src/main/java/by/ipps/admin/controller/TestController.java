package by.ipps.admin.controller;

import by.ipps.admin.entity.User;
import by.ipps.admin.exception.InvalidJwtAuthenticationException;
import by.ipps.admin.utils.JwtTokenUtil;
import by.ipps.admin.utils.resttemplate.UserRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
public class TestController {

    private final UserRestTemplate rest;

    public TestController(UserRestTemplate userRestTemplate, JwtTokenUtil jwtTokenUtil) {
        this.rest = userRestTemplate;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping(value = "/hello")
    @ResponseBody
    @PreAuthorize("hasAuthority('SuperEditor')")
    public String hello(){
        return "Hello World!";
    }


    @GetMapping(value = "/authenticate")
    @ResponseBody
    public String hello1(){
        return "Hello World!";
    }

    @GetMapping(value = "/getInfo")
    @ResponseBody
    public User getBaseInfo(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        if (!username.equals("anonymousUser")){
            User user = rest.getUserByLogin(username);
            return new User(null, null, user.getName(), username, "patronic", user.getRoles());
        } else
            return null;
    }

    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) throws InvalidJwtAuthenticationException {
        jwtTokenUtil.revokeToken(jwtTokenUtil.resolveToken(request));
        return ok().build();
    }
}
