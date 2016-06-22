package org.grizz.integration.test;

import org.grizz.TestContext;
import org.grizz.config.security.SecurityConfig;
import org.grizz.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, SecurityConfig.class})
@WebAppConfiguration
public class SecurityIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldReturnStatus401WhenNotLogin() throws Exception {
        String ID = "1";
        mockMvc.perform(get("/planets/" + ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldReturnStatus200WhenAuthorized() throws Exception {
        String ID = "1";
        mockMvc.perform(get("/planets/" + ID))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRedirectToLogoutSuccessWhenLoggingOut() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logout/success"));
    }

    @Test
    @WithMockUser(username = "some_username")
    public void shouldReturnProperCurrentUser() throws Exception {
        String currentUserLogin = userService.getCurrentUserLogin();
        assertThat(currentUserLogin, equalTo("some_username"));
    }

    @Ignore
    @Test
    public void shouldFailOnInvalidCSRFToken() throws Exception {
        mockMvc.perform(post("/").with(csrf().useInvalidToken())).andExpect(status().isUnauthorized());
    }

    @Ignore
    @Test
    @WithMockUser(roles = {"USER"})
    public void shouldRedirectToDeniedErrorPageWhenAccessingUnauthorizedResource() throws Exception {
        mockMvc.perform(get("/somePageWithAdminRights"))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/denied"));
    }
}