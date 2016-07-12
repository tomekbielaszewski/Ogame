package org.grizz.service;

import com.google.common.collect.Sets;
import org.grizz.exception.UserAlreadyExistException;
import org.grizz.exception.UserBadPasswordException;
import org.grizz.model.User;
import org.grizz.model.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.springframework.security.crypto.bcrypt.BCrypt.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanetService planetService;

    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public String getCurrentUserLogin() {
        Object principal = getAuthenticationContext();
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
            return currentUser;
        } else {
            throw new UserBadPasswordException();
        }
    }

    public User createUser(String login, String password) {
        User alreadyExisting = getByLogin(login);
        if (alreadyExisting == null) {
            User user = User.builder()
                    .login(login)
                    .passwordHash(hashpw(password, gensalt()))
                    .roles(player())
                    .build();
            User newUser = userRepository.save(user);
            planetService.create(newUser);

            return newUser;
        } else {
            throw new UserAlreadyExistException(login);
        }
    }

    protected Object getAuthenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Set<String> player() {
        return Sets.newHashSet(User.PLAYER_ROLE);
    }
}
