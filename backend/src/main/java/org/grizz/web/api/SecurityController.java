package org.grizz.web.api;

import lombok.extern.slf4j.Slf4j;
import org.grizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SecurityController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/logout/success")
    public void logoutSuccess() {
    }

    @RequestMapping(value = "/denied")
    public void denied() {
        String currentUser = userService.getCurrentUserLogin();
        if (currentUser != null) {
            log.warn("User [{}] tried to access prohibited area", currentUser);
        }
    }
}
