package org.grizz.config.security.oauth2;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.grizz.exception.UserAuthenticationException;
import org.grizz.model.Player;
import org.grizz.model.repos.PlayerRepository;
import org.grizz.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.client.filter.OAuth2AuthenticationFailureEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationEventsListener {
    @Autowired
    private PlayerService playerService;

    @EventListener
    public void handleOAuth2AuthenticationFailureEvent(OAuth2AuthenticationFailureEvent event) {
        throw new UserAuthenticationException();
    }

    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        String oAuthID = event.getAuthentication().getName();
        log.info(oAuthID + " has logged in...");

        if(!playerExists(oAuthID)) {
            register(oAuthID, "Grizz");
        }
    }

    private boolean playerExists(String oAuthID) {
        return playerService.exists(oAuthID);
    }

    private void register(String oAuthID, String login) {
        playerService.createUser(oAuthID, login);
    }
}
