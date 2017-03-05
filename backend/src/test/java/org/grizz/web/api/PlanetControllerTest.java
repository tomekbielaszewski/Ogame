package org.grizz.web.api;

import com.google.common.collect.Sets;
import org.grizz.exception.PlanetNotFoundException;
import org.grizz.i18n.Localization;
import org.grizz.model.Building;
import org.grizz.model.Planet;
import org.grizz.model.enummerations.BuildingType;
import org.grizz.service.PlanetService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PlanetControllerTest {
    private static String ID = "some_id";
    private static String NOT_EXISTING_ID = "another_id";
    private static BuildingType type = BuildingType.CRYSTAL_MINE;
    private static int level = 1;
    private Set<Building> buildings = Sets.newHashSet();

    private MockMvc mockMvc;

    @Mock
    private PlanetService planetService;

    @InjectMocks
    private PlanetController controller = new PlanetController();

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnPlanetWhenGivenProperID() throws Exception {
        when(planetService.get(ID)).thenReturn(dummyPlanet(ID));
        mockMvc.perform(get("/planets/" + ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.buildings[0].type").value(type.name()))
                .andExpect(jsonPath("$.buildings[0].level").value(level));
    }

    @Test
    public void shouldReturnStatus404WithProperMessageWhenGivenNotExistingID() throws Exception {
        when(planetService.get(NOT_EXISTING_ID)).thenThrow(new PlanetNotFoundException(NOT_EXISTING_ID));
        mockMvc.perform(get("/planets/" + NOT_EXISTING_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.message").value(Localization.EXCEPTION_PLANET_NOT_FOUND))
                .andExpect(jsonPath("$.cause").value(NOT_EXISTING_ID));
    }

    private Planet dummyPlanet(String id) {
        buildings.add(Building.builder().level(level).type(type).build());
        return Planet.builder().id(id).buildings(buildings).build();
    }

}

