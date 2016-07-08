package org.grizz.service;

import lombok.extern.slf4j.Slf4j;
import org.grizz.OgameCloneApplication;
import org.grizz.config.security.SecurityConfig;
import org.grizz.exception.UserBadPasswordException;
import org.grizz.model.User;
import org.grizz.model.repos.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OgameCloneApplication.class, SecurityConfig.class})
public class UserServiceTest {

    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "user")
    public void shouldReturnProperUserLogin() {
        String expectedLogin = "user";

        String currentUserLogin = userService.getCurrentUserLogin();

        assertThat(currentUserLogin, equalTo(expectedLogin));
    }

    @Test
    @WithMockUser(username = "user", password = "user")
    public void shouldChangingPasswordPossibleWhenGivenProperOldPassword() {
        String oldPassword = "user";
        String newPassword = "newpassword";

        User user = userService.changePassword(oldPassword, newPassword);

        boolean passwordChanged = BCrypt.checkpw(newPassword, user.getPasswordHash());
        assertTrue(passwordChanged);
        verify(userRepository).save(user);
    }

    @Test(expected = UserBadPasswordException.class)
    @WithMockUser(username = "user", password = "user")
    public void shouldThrowExceptionWhenGivenImproperOldPassword() {
        String oldPassword = "bad_password";
        String newPassword = "newpassword";

        userService.changePassword(oldPassword, newPassword);
    }

    @Test
    public void shouldIncreaseNumberOfPlanetOnCurrentUser() {
        User user = userService.increaseNumberOfPlanetsAndSave(sampleUser());

        int oldPlanetNumber = sampleUser().getPlanetNumber();
        System.out.println(user);
        int newPlantNumber = user.getPlanetNumber();

        assertTrue(newPlantNumber == oldPlanetNumber + 1);
    }

    @Test
    public void shouldReturnTrueWhenNumberOfPlanetIsLessThenMaxPlanetNumber() {
        User user = sampleUser();
        assertTrue(userService.canHaveMorePlanet(user));
    }

    @Test
    public void shouldReturnFalseWhenNumberOfPlanetIsEqualMaxPlanetNumber() {
        User user = sampleUser();
        user.setMaxPlanetNumber(user.getPlanetNumber());
        assertFalse(userService.canHaveMorePlanet(user));
    }

    @Test
    public void shouldReturnFalseWhenNumberOfPlanetIsBiggerThanMaxPlanetNumber() {
        User user = sampleUser();
        user.setMaxPlanetNumber(user.getPlanetNumber() - 1);
        assertFalse(userService.canHaveMorePlanet(user));
    }

    private User sampleUser() {
        return User.builder().login("login").planetNumber(4).maxPlanetNumber(8).build();
    }


}