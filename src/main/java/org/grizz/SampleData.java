package org.grizz;

import lombok.extern.slf4j.Slf4j;
import org.grizz.model.Building;
import org.grizz.model.Planet;
import org.grizz.model.repos.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
public class SampleData {

    @Autowired
    PlanetRepository planetRepository;

    @PostConstruct
    public Planet createSampleNewPlanet() {

        Set<Building> buildings = new HashSet<>();
        buildings.add(Building.builder().level(1).type("Metal mind").build());
        buildings.add(Building.builder().level(1).type("Crystal mind").build());
        buildings.add(Building.builder().level(1).type("Deuterium mind").build());
        planetRepository.deleteAll();
        return planetRepository.insert(Planet.builder().id("Sample planet").buildings(buildings).build());
    }

}
