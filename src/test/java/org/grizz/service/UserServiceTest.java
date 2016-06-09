package org.grizz.service;

import org.grizz.OgameCloneApplication;
import org.grizz.config.security.SecurityConfig;
import org.grizz.exception.UserBadPasswordException;
import org.grizz.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OgameCloneApplication.class, SecurityConfig.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

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
    }

    @Test(expected = UserBadPasswordException.class)
    @WithMockUser(username = "user", password = "user")
    public void shouldThrowExceptionWhenGivenImproperOldPassword() {
        String oldPassword = "bad_password";
        String newPassword = "newpassword";

        userService.changePassword(oldPassword, newPassword);
    }
}