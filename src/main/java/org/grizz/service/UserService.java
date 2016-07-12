package org.grizz.service;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.grizz.exception.UserAlreadyExistException;
import org.grizz.exception.UserBadPasswordException;
import org.grizz.model.User;
import org.grizz.model.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static org.springframework.security.crypto.bcrypt.BCrypt.*;

@Slf4j
@Service
public class UserService {

    private static int MAX_NUMBER_OF_PLANET = 8;
    @Autowired
    private PlanetService planetService;
    @Autowired
    private UserRepository userRepository;

    public void createUser(String login, String password) {
        try {
            User newUser = userRepository.insert(User.builder().login(login).passwordHash(hashpw(password, gensalt()))
                    .planetNumber(0).maxPlanetNumber(MAX_NUMBER_OF_PLANET).build());
            planetService.createPlanet(newUser);
            log.info("New player has created with login [" + login + "]");
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExistException(e.getMessage());
        }

    }

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

    public User increaseNumberOfPlanetsAndSave(User currentUser) {
        int actualPlanetNumber = currentUser.getPlanetNumber();
        currentUser.setPlanetNumber(++actualPlanetNumber);
        saveUser(currentUser);
        return currentUser;
    }

    public User saveUser(User userToSave) {
        log.debug("Saved user : ", userToSave);
        return userRepository.save(userToSave);
    }

    public boolean canHaveMorePlanet(User user) {
        return user.getPlanetNumber() < user.getMaxPlanetNumber();
    }
}
