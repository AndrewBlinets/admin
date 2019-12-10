package by.ipps.admin.service;

import by.ipps.admin.utils.resttemplate.UserRestTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRestTemplate restRequestToDao;

    public JwtUserDetailsService(UserRestTemplate userRestTemplate) {
        this.restRequestToDao = userRestTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        by.ipps.admin.entity.User user = restRequestToDao.getUserByLogin(username);
            return new User(user.getLogin(), user.getHashPassword(),
//                    "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    getAuthorities(user.getRoles()));
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            List<String> roles) {

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