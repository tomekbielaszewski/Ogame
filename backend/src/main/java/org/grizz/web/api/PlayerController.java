package org.grizz.web.api;

import com.fasterxml.jackson.annotation.JsonView;
import org.grizz.exception.UserAlreadyExistException;
import org.grizz.exception.UserBadPasswordException;
import org.grizz.exception.ValidationException;
import org.grizz.model.User;
import org.grizz.service.UserService;
import org.grizz.web.api.request.UserCreateRequest;
import org.grizz.web.api.request.UserPasswordChangeRequest;
import org.grizz.web.api.response.ExceptionResponse;
import org.grizz.web.api.views.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/current", method = GET)
    public User currentUser() {
        return userService.getCurrentUser();
    }

    @RequestMapping(method = POST)
    public User createUser(@Valid @RequestBody UserCreateRequest user) {
        return userService.createUser(user.getLogin(), user.getPassword());
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User changePassword(@Valid @RequestBody UserPasswordChangeRequest user) {
        return userService.changePassword(user.getOldPassword(), user.getNewPassword());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistException.class)
    public ExceptionResponse handleUserAlreadyExist(UserAlreadyExistException e) {
        return ExceptionResponse.of(e.getMessage(), e.getLogin());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserBadPasswordException.class)
    public ExceptionResponse handleBadPassword(UserBadPasswordException e) {
        return ExceptionResponse.of(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleBadPassword(MethodArgumentNotValidException e) {
        ValidationException validationException = new ValidationException(e);
        return ExceptionResponse.of(
                validationException.getMessage(),
                validationException.getAllErrors());
    }
}
