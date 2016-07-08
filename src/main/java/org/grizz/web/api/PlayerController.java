package org.grizz.web.api;

import org.grizz.model.User;
import org.grizz.service.UserService;
import org.grizz.web.api.request.NewUserCreateRequest;
import org.grizz.web.api.request.UserPasswordChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User changePassword(@RequestBody UserPasswordChangeRequest user) {
        return userService.changePassword(user.getOldPassword(), user.getNewPassword());
    }

    @RequestMapping(method = POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createUser(@Validated @RequestBody NewUserCreateRequest user) {
        userService.createUser(user.getLogin(), user.getPassword());
    }

}
