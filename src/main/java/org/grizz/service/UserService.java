package org.grizz.service;

import com.google.common.collect.Sets;
import org.grizz.exception.UserBadPasswordException;
import org.grizz.model.User;
import org.grizz.model.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static org.springframework.security.crypto.bcrypt.BCrypt.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getByLogin(String login) {
        return User.builder()
                .login(login)
                .roles(Sets.newHashSet("PLAYER"))
                .passwordHash(hashpw(login, gensalt()))
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

    public User getCurrentUser() {
        return getByLogin(getCurrentUserLogin());
    }

    public User changePassword(String oldPassword, String newPassword) {
        User currentUser = getCurrentUser();
        if (checkpw(oldPassword, currentUser.getPasswordHash())) {
            currentUser.setPasswordHash(hashpw(newPassword, gensalt()));
            userRepository.save(currentUser);
        } else {
            throw new UserBadPasswordException();
        }
        return currentUser;
    }
}
