package org.grizz.web.api;

import com.google.common.collect.Sets;
import org.grizz.model.Player;
import org.grizz.service.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerControllerTest {
    private final String ID = "123";
    private final String LOGIN = "login";
    private final Set<String> PLAYER_ROLES = Sets.newHashSet(Player.PLAYER_ROLE);

    private MockMvc mockMvc;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController controller = new PlayerController();

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnCurrentUser() throws Exception {
        when(playerService.getCurrentUser()).thenReturn(dummyPlayer());
        mockMvc.perform(get("/players/current")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.login").value(LOGIN))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles").value(hasItems(Player.PLAYER_ROLE)))
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }


    private Player dummyPlayer() {
        return Player.builder()
                .roles(Sets.newHashSet(PLAYER_ROLES))
                .id(ID)
                .login(LOGIN)
                .build();
    }
}

