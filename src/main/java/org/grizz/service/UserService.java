package org.grizz.service;

import com.google.common.collect.Sets;
import org.grizz.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getByLogin(String login) {
        return User.builder()
                .login(login)
                .roles(Sets.newHashSet("PLAYER"))
                .passwordHash(BCrypt.hashpw(login, BCrypt.gensalt()))
                .build();
    }

    public String getCurrentUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserLogin;

        if (principal instanceof UserDetails) {
            currentUserLogin = ((UserDetails) principal).getUsername();
        } else {
            currentUserLogin = principal.toString();
        }

        return currentUserLogin;
    }
}
