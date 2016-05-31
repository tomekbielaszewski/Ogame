package org.grizz.config.security;

import com.google.common.collect.Sets;
import org.grizz.exception.UserAuthenticationException;
import org.grizz.model.User;
import org.grizz.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.crypto.bcrypt.BCrypt.gensalt;
import static org.springframework.security.crypto.bcrypt.BCrypt.hashpw;

@RunWith(MockitoJUnitRunner.class)
public class RestAuthenticationProviderTest {
    private String login = "login";
    private String plainPassword = "password";
    private String hashedPassword = hashpw("password", gensalt());
    private User user = User.builder().login(login).passwordHash(hashedPassword).roles(Sets.newHashSet()).build();

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationProvider authenticationProvider = new RestAuthenticationProvider();

    @Before
    public void setUp() {
        when(userService.getByLogin(login)).thenReturn(user);
        when(authentication.getName()).thenReturn(login);
        when(authentication.getCredentials()).thenReturn(plainPassword);
    }

    @Test(expected = UserAuthenticationException.class)
    public void shouldThrowExceptionWhenIncorrectPassword() {
        when(authentication.getCredentials()).thenReturn("incorrectPassword");
        authenticationProvider.authenticate(authentication);
    }

    @Test(expected = UserAuthenticationException.class)
    public void shouldThrowExceptionWhenIncorrectUsername() {
        when(authentication.getName()).thenReturn("incorrectLogin");
        authenticationProvider.authenticate(authentication);
    }

    @Test
    public void shouldCreateValidTokenWhenLoginSuccessful() {
        user.getRoles().add("USER");
        Authentication token = authenticationProvider.authenticate(authentication);

        assertThat(token.getPrincipal(), equalTo(login));
        assertThat(token.getCredentials(), equalTo(plainPassword));
        assertThat(token.getAuthorities(), hasSize(1));
        assertThat(token.getAuthorities(), Matchers.contains(new SimpleGrantedAuthority("USER")));
    }
}