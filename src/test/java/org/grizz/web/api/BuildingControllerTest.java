package org.grizz.web.api;

import org.grizz.model.Building;
import org.grizz.service.BuildingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class BuildingControllerTest {
    private static String SOME_NAME = "some_name";

    private MockMvc mockMvc;

    @Mock
    private BuildingService buildingService;

    @InjectMocks
    private BuildingController controller = new BuildingController();

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when(buildingService.getByType(SOME_NAME)).thenReturn(dummyBuilding(SOME_NAME));
    }

    @Test
    public void shouldReturnBuildingWhenGivenProperName() throws Exception {
        mockMvc.perform(get("/buildings/" + SOME_NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.type").value(SOME_NAME));
    }

    private Building dummyBuilding(String name) {
        return Building.builder().type(name).level(1).build();
    }
}
