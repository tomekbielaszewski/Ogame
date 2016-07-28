package org.grizz.web.api;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.grizz.exception.UserAlreadyExistException;
import org.grizz.exception.UserBadPasswordException;
import org.grizz.i18n.Localization;
import org.grizz.model.User;
import org.grizz.service.UserService;
import org.grizz.web.api.request.UserCreateRequest;
import org.grizz.web.api.request.UserPasswordChangeRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class PlayerControllerTest {
    private final String ID = "123";
    private final String LOGIN = "login";
    private final String TOO_SHORT_LOGIN = "lo";
    private final String PASSWORD = "password";
    private final String TOO_SHORT_PASSWORD = "passwor";
    private final String OLD_PASSWORD = "old_password";
    private final String NEW_PASSWORD = "new_password";
    private final String TOO_SHORT_NEW_PASSWORD = "new_pas";
    private final Set<String> PLAYER_ROLES = Sets.newHashSet(User.PLAYER_ROLE);
    private final Set<String> ADMIN_ROLES = Sets.newHashSet(User.ADMIN_ROLE, User.PLAYER_ROLE);

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private PlayerController controller = new PlayerController();

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when(userService.changePassword(any(), any())).thenReturn(dummyPlayer());
        when(userService.createUser(any(), any())).thenReturn(dummyPlayer());
    }

    @Test
    @WithMockUser
    public void shouldReturnUserWhenChangingPassword() throws Exception {
        Gson gson = new Gson();
        UserPasswordChangeRequest request = UserPasswordChangeRequest.builder()
                .newPassword(NEW_PASSWORD)
                .oldPassword(OLD_PASSWORD)
                .build();
        String json = gson.toJson(request, UserPasswordChangeRequest.class);

        mockMvc.perform(put("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.login").value(LOGIN))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles").value(hasItems(User.PLAYER_ROLE)))
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    @WithMockUser
    public void shouldReturnExceptionWhenChangedPasswordTooShort() throws Exception {
        Gson gson = new Gson();
        UserPasswordChangeRequest request = UserPasswordChangeRequest.builder()
                .newPassword(TOO_SHORT_NEW_PASSWORD)
                .oldPassword(OLD_PASSWORD)
                .build();
        String json = gson.toJson(request, UserPasswordChangeRequest.class);

        mockMvc.perform(put("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(Localization.EXCEPTION_VALIDATION))//;
                .andExpect(jsonPath("$.cause[0].defaultMessage").value(Localization.VALIDATION_USER_PASSWORD_SIZE))
                .andExpect(jsonPath("$.cause[0].field").value("newPassword"));
    }

    @Test
    @WithMockUser
    public void shouldReturnExceptionWhenChangedPasswordIsNull() throws Exception {
        Gson gson = new Gson();
        UserPasswordChangeRequest request = UserPasswordChangeRequest.builder()
                .newPassword(null)
                .oldPassword(OLD_PASSWORD)
                .build();
        String json = gson.toJson(request, UserPasswordChangeRequest.class);

        mockMvc.perform(put("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(Localization.EXCEPTION_VALIDATION))//;
                .andExpect(jsonPath("$.cause[0].defaultMessage").value(Localization.VALIDATION_USER_PASSWORD_NOTNULL))
                .andExpect(jsonPath("$.cause[0].field").value("newPassword"));
    }

    @Test
    public void shouldReturnExceptionWhenChangingPasswordAndGivenBadOldPassword() throws Exception {
        when(userService.changePassword(OLD_PASSWORD, NEW_PASSWORD)).thenThrow(new UserBadPasswordException());
        Gson gson = new Gson();
        UserPasswordChangeRequest request = UserPasswordChangeRequest.builder()
                .newPassword(NEW_PASSWORD)
                .oldPassword(OLD_PASSWORD)
                .build();
        String json = gson.toJson(request, UserPasswordChangeRequest.class);

        mockMvc.perform(put("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(Localization.EXCEPTION_BAD_PASSWORD))//;
                .andExpect(jsonPath("$.cause").doesNotExist());
    }

    @Test
    public void shouldReturnUserWhenCreatingNewOne() throws Exception {
        Gson gson = new Gson();
        UserCreateRequest request = UserCreateRequest.builder()
                .login(LOGIN)
                .password(PASSWORD)
                .build();
        String json = gson.toJson(request, UserCreateRequest.class);

        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.login").value(LOGIN))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles").value(hasItems(User.PLAYER_ROLE)))
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    public void shouldReturnExceptionWhenNewUsersLoginTooShort() throws Exception {
        Gson gson = new Gson();
        UserCreateRequest request = UserCreateRequest.builder()
                .login(TOO_SHORT_LOGIN)
                .password(PASSWORD)
                .build();
        String json = gson.toJson(request, UserCreateRequest.class);

        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(Localization.EXCEPTION_VALIDATION))//;
                .andExpect(jsonPath("$.cause[0].defaultMessage").value(Localization.VALIDATION_USER_LOGIN_SIZE))
                .andExpect(jsonPath("$.cause[0].field").value("login"));
    }

    @Test
    public void shouldReturnExceptionWhenNewUsersPasswordTooShort() throws Exception {
        Gson gson = new Gson();
        UserCreateRequest request = UserCreateRequest.builder()
                .login(LOGIN)
                .password(TOO_SHORT_PASSWORD)
                .build();
        String json = gson.toJson(request, UserCreateRequest.class);

        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(Localization.EXCEPTION_VALIDATION))//;
                .andExpect(jsonPath("$.cause[0].defaultMessage").value(Localization.VALIDATION_USER_PASSWORD_SIZE))
                .andExpect(jsonPath("$.cause[0].field").value("password"));
    }

    @Test
    public void shouldReturnExceptionWhenNewUsersLoginIsNull() throws Exception {
        Gson gson = new Gson();
        UserCreateRequest request = UserCreateRequest.builder()
                .login(null)
                .password(PASSWORD)
                .build();
        String json = gson.toJson(request, UserCreateRequest.class);

        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(Localization.EXCEPTION_VALIDATION))//;
                .andExpect(jsonPath("$.cause[0].defaultMessage").value(Localization.VALIDATION_USER_LOGIN_NOTNULL))
                .andExpect(jsonPath("$.cause[0].field").value("login"));
    }

    @Test
    public void shouldReturnExceptionWhenNewUsersPasswordIsNull() throws Exception {
        Gson gson = new Gson();
        UserCreateRequest request = UserCreateRequest.builder()
                .login(LOGIN)
                .password(null)
                .build();
        String json = gson.toJson(request, UserCreateRequest.class);

        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(Localization.EXCEPTION_VALIDATION))//;
                .andExpect(jsonPath("$.cause[0].defaultMessage").value(Localization.VALIDATION_USER_PASSWORD_NOTNULL))
                .andExpect(jsonPath("$.cause[0].field").value("password"));
    }

    @Test
    public void shouldReturnExceptionWhenNewUsersAlreadyExist() throws Exception {
        when(userService.createUser(LOGIN, PASSWORD)).thenThrow(new UserAlreadyExistException(LOGIN));
        Gson gson = new Gson();
        UserCreateRequest request = UserCreateRequest.builder()
                .login(LOGIN)
                .password(PASSWORD)
                .build();
        String json = gson.toJson(request, UserCreateRequest.class);

        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(Localization.EXCEPTION_USER_ALREADY_EXIST))//;
                .andExpect(jsonPath("$.cause").value(LOGIN));
    }

    private User dummyPlayer() {
        return User.builder()
                .roles(Sets.newHashSet(PLAYER_ROLES))
                .id(ID)
                .login(LOGIN)
                .build();
    }
}

