package org.grizz.web.api;

import org.grizz.model.Player;
import org.grizz.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/current", method = GET)
    public Player currentPlayer() {
        return playerService.getCurrentUser();
    }
}
