package org.grizz.service;

import com.google.common.collect.Sets;
import org.grizz.exception.UserAlreadyExistException;
import org.grizz.exception.PlayerBadPasswordException;
import org.grizz.model.Player;
import org.grizz.model.repos.PlayerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {
    private final String ID = "123";
    private final String LOGIN = "login";
    private final Set<String> PLAYER_ROLES = Sets.newHashSet(Player.PLAYER_ROLE);

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlanetService planetService;

    @Mock
    private UserDetails authenticationContext;

    @InjectMocks
    private PlayerServiceWithMockedAuthenticationContext userService;

    @Test
    public void shouldReturnProperUserLogin() {
        when(authenticationContext.getUsername()).thenReturn(LOGIN);

        String currentUserLogin = userService.getCurrentPlayerLogin();

        assertThat(currentUserLogin, equalTo(LOGIN));
    }

    @Test
    public void shouldCreateNewUserWithItsOwnPlanet() {
        when(playerRepository.findByLogin(LOGIN)).thenReturn(null);
        when(playerRepository.save(any(Player.class))).thenReturn(dummyPlayer());

        Player player = userService.createUser(ID, LOGIN);

        verify(playerRepository).save(any(Player.class));
        verify(planetService).create(player);
    }

    @Test
    public void shouldThrowExceptionWhenUserAlreadyExist() {
        when(playerRepository.exists(ID)).thenReturn(true);

        try {
            userService.createUser(ID, LOGIN);
            fail("UserAlreadyExistException was not thrown");
        } catch (UserAlreadyExistException e) {
            assertThat(e.getLogin(), equalTo(LOGIN));
        }

        verify(playerRepository, never()).save(any(Player.class));
        verify(planetService, never()).create(any());
    }

    private Player dummyPlayer() {
        return Player.builder()
                .roles(PLAYER_ROLES)
                .id(ID)
                .login(LOGIN)
                .build();
    }
}