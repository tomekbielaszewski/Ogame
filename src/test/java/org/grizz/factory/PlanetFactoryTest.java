package org.grizz.factory;

import lombok.extern.slf4j.Slf4j;
import org.grizz.OgameCloneApplication;
import org.grizz.config.security.SecurityConfig;
import org.grizz.model.Building;
import org.grizz.model.Planet;
import org.grizz.model.User;
import org.grizz.model.enummerations.BuildingType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OgameCloneApplication.class, SecurityConfig.class})
public class PlanetFactoryTest {

    private static String NAME = "Planet Mother";

    @Autowired
    @InjectMocks
    PlanetFactory planetFactory;

    @Test
    public void shouldReturnPlanetWithDefaultNameAndGivenOwner() {
        Planet planet = planetFactory.createDefaultPlanet(dummyUser());
        assertThat(planet.getOwnerLogin(), equalTo(dummyUser().getLogin()));
        assertThat(planet.getName(), equalTo(NAME));
    }

    @Test
    public void shouldReturnPlanetWithBuildingListWithDefaultLevel() {
        Planet planet = planetFactory.createDefaultPlanet(dummyUser());
        Set<Building> buildings = planet.getBuildings();
        assertNotNull(buildings);
        assertTrue(buildings.containsAll(buildingsList()));

    }

    private Set<Building> buildingsList() {
        Set<Building> buildings = new HashSet<>();
        buildings.add(Building.builder().type(BuildingType.EXTRACTOR_DEUTERIUM)
                .level(BuildingType.EXTRACTOR_DEUTERIUM.getLevel()).build());
        buildings.add(Building.builder().type(BuildingType.FUSION_REACTOR)
                .level(BuildingType.FUSION_REACTOR.getLevel()).build());
        buildings.add(Building.builder().type(BuildingType.CRYSTAL_MINE)
                .level(BuildingType.CRYSTAL_MINE.getLevel()).build());
        buildings.add(Building.builder().type(BuildingType.MAGAZINE_METALS)
                .level(BuildingType.MAGAZINE_METALS.getLevel()).build());
        return buildings;
    }

    private User dummyUser() {
        return User.builder().login("login").passwordHash("pass").planetNumber(0).build();
    }

}
