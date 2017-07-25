package org.grizz.service;

import com.google.common.collect.Sets;
import org.grizz.exception.UserAlreadyExistException;
import org.grizz.model.Player;
import org.grizz.model.repos.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlanetService planetService;

    public Player getCurrentUser() {
        return getByLogin(getCurrentPlayerLogin());
    }

    public Player getByLogin(String login) {
        return playerRepository.findByLogin(login);
    }

    public String getCurrentPlayerLogin() {
        Object principal = getAuthenticationContext();
        String currentUserLogin;

        if (principal instanceof UserDetails) {
            currentUserLogin = ((UserDetails) principal).getUsername();
        } else {
            currentUserLogin = principal.toString();
        }

        return currentUserLogin;
    }

    public Player createUser(String id, String login) {
        if (exists(id)) {
            throw new UserAlreadyExistException(login);
        }

        Player player = Player.builder()
                .id(id)
                .login(login)
                .roles(player())
                .build();
        Player newPlayer = playerRepository.save(player);
        planetService.create(newPlayer);

        return newPlayer;
    }

    public boolean exists(String id) {
        return playerRepository.exists(id);
    }

    protected Object getAuthenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Set<String> player() {
        return Sets.newHashSet(Player.PLAYER_ROLE);
    }
}
