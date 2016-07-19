package org.grizz.service;

import com.google.common.collect.Sets;
import org.grizz.exception.UserAlreadyExistException;
import org.grizz.exception.UserBadPasswordException;
import org.grizz.model.User;
import org.grizz.model.repos.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private final String ID = "123";
    private final String LOGIN = "login";
    private final String PASSWORD = "password";
    private final String OLD_PASSWORD = "password";
    private final String NEW_PASSWORD = "new_password";
    private final Set<String> PLAYER_ROLES = Sets.newHashSet(User.PLAYER_ROLE);

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlanetService planetService;

    @Mock
    private UserDetails authenticationContext;

    @InjectMocks
    private UserServiceWithMockedAuthenticationContext userService;

    @Test
    public void shouldReturnProperUserLogin() {
        when(authenticationContext.getUsername()).thenReturn(LOGIN);

        String currentUserLogin = userService.getCurrentUserLogin();

        assertThat(currentUserLogin, equalTo(LOGIN));
    }

    @Test
    public void shouldChangingPasswordPossibleWhenGivenProperOldPassword() {
        when(authenticationContext.getUsername()).thenReturn(LOGIN);
        when(userRepository.findByLogin(LOGIN)).thenReturn(dummyPlayer());

        User user = userService.changePassword(OLD_PASSWORD, NEW_PASSWORD);

        boolean passwordChanged = BCrypt.checkpw(NEW_PASSWORD, user.getPasswordHash());
        assertTrue(passwordChanged);
        verify(userRepository).save(user);
    }

    @Test
    public void shouldThrowExceptionWhenGivenImproperOldPassword() {
        when(authenticationContext.getUsername()).thenReturn(LOGIN);
        when(userRepository.findByLogin(LOGIN)).thenReturn(dummyPlayer());

        try {
            userService.changePassword(OLD_PASSWORD + "bad_pass", NEW_PASSWORD);
            fail("UserBadPasswordException was not thrown");
        } catch (UserBadPasswordException e) {

        }
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldCreateNewUserWithItsOwnPlanet() {
        when(userRepository.findByLogin(LOGIN)).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(dummyPlayer());

        User user = userService.createUser(LOGIN, PASSWORD);

        boolean passwordHashed = BCrypt.checkpw(PASSWORD, user.getPasswordHash());
        assertTrue(passwordHashed);
        verify(userRepository).save(any(User.class));
        verify(planetService).create(user);
    }

    @Test
    public void shouldThrowExceptionWhenUserAlreadyExist() {
        when(userRepository.findByLogin(LOGIN)).thenReturn(dummyPlayer());

        try {
            userService.createUser(LOGIN, PASSWORD);
            fail("UserAlreadyExistException was not thrown");
        } catch (UserAlreadyExistException e) {
            assertThat(e.getLogin(), equalTo(LOGIN));
        }

        verify(userRepository, never()).save(any(User.class));
        verify(planetService, never()).create(any());
    }

    private User dummyPlayer() {
        return User.builder()
                .roles(Sets.newHashSet(PLAYER_ROLES))
                .id(ID)
                .login(LOGIN)
                .passwordHash(BCrypt.hashpw(PASSWORD, BCrypt.gensalt()))
                .build();
    }
}