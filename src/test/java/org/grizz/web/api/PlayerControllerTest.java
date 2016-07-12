package org.grizz.web.api;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.grizz.model.User;
import org.grizz.service.UserService;
import org.grizz.web.api.request.NewUserCreateRequest;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class PlayerControllerTest {
    private final String LOGIN = "login";
    private final String SHORT_LOGIN = "lo";
    private final String SHORT_PASSWORD = "pass";
    private final String OLD_PASSWORD = "old_password";
    private final String NEW_PASSWORD = "new_password";

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private PlayerController controller = new PlayerController();

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when(userService.changePassword(OLD_PASSWORD, NEW_PASSWORD)).thenReturn(dummyUser());
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
                .andExpect(jsonPath("$.login").value(LOGIN));
    }

    @Test
    public void shouldCreateNewUserWhenGivenLoginAndPassword() throws Exception {
        Gson gson = new Gson();
        NewUserCreateRequest request = NewUserCreateRequest.builder()
                .login(LOGIN)
                .password(NEW_PASSWORD)
                .build();
        String json = gson.toJson(request, NewUserCreateRequest.class);
        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowExceptionWhenLoginIsTooShort() throws Exception {
        Gson gson = new Gson();
        NewUserCreateRequest request = NewUserCreateRequest.builder()
                .login(SHORT_LOGIN)
                .password(NEW_PASSWORD)
                .build();
        String json = gson.toJson(request, NewUserCreateRequest.class);
        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowExceptionWhenPasswordIsTooShort() throws Exception {
        Gson gson = new Gson();
        NewUserCreateRequest request = NewUserCreateRequest.builder()
                .login(LOGIN)
                .password(SHORT_PASSWORD)
                .build();
        String json = gson.toJson(request, NewUserCreateRequest.class);
        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    private User dummyUser() {
        return User.builder().roles(Sets.newHashSet()).login(LOGIN).build();
    }
}

