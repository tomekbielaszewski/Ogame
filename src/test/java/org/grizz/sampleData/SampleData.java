package org.grizz.sampleData;

import org.grizz.OgameCloneApplication;
import org.grizz.model.Building;
import org.grizz.model.Planet;
import org.grizz.model.enummerations.BuildingType;
import org.grizz.model.repos.PlanetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OgameCloneApplication.class)
@WebAppConfiguration
public class SampleData {

    @Autowired
    PlanetRepository planetRepository;

    //@Ignore
    @Test
    public void createSampleNewPlanet() {
        planetRepository.deleteAll();
        Set<Building> buildings = new HashSet<>();
        buildings.add(Building.builder().level(1).type(BuildingType.METAL_MINE).build());
        buildings.add(Building.builder().level(1).type(BuildingType.CRYSTAL_MINE).build());
        buildings.add(Building.builder().level(1).type(BuildingType.EXTRACTOR_DEUTERIUM).build());

        planetRepository.save(Planet.builder().id("Sample planet").buildings(buildings).build());
    }

}
