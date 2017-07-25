package org.grizz.web.api;

import lombok.extern.slf4j.Slf4j;
import org.grizz.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SecurityController {
    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/logout/success")
    public void logoutSuccess() {
    }

    @RequestMapping(value = "/denied")
    public void denied() {
        String currentPlayer = playerService.getCurrentPlayerLogin();
        if (currentPlayer != null) {
            log.warn("Player [{}] tried to access prohibited area", currentPlayer);
        }
    }
}
