package org.grizz.config.security;

import com.google.common.collect.Sets;
import org.grizz.model.User;
import org.grizz.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {
    private static final String LOGIN = "test_login";
    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    public void checkInteractionWithService() {
        when(userService.getByLogin(LOGIN)).thenReturn(User.builder().login(LOGIN).build());

        UserDetails userDetails = userDetailsService.loadUserByUsername(LOGIN);

        assertThat(userDetails.getUsername(), equalTo(LOGIN));
        verify(userService).getByLogin(eq(LOGIN));
    }


    @Test
    public void properlyCreatesListOfAuthorities() {
        String auth1 = "AUTH_1";
        String auth2 = "AUTH_2";
        SimpleGrantedAuthority auth1AsSGA = new SimpleGrantedAuthority(auth1);
        SimpleGrantedAuthority auth2AsSGA = new SimpleGrantedAuthority(auth2);
        HashSet<String> roles = Sets.newHashSet(auth1, auth2);
        when(userService.getByLogin(LOGIN)).thenReturn(User.builder().roles(roles).build());

        UserDetails userDetails = userDetailsService.loadUserByUsername(LOGIN);

        assertThat(userDetails.getAuthorities(), containsInAnyOrder(auth1AsSGA, auth2AsSGA));
        verify(userService).getByLogin(eq(LOGIN));
    }
}