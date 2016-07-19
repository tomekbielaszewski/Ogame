package org.grizz.service;

import com.google.common.collect.Lists;
import org.grizz.model.Building;
import org.grizz.model.Planet;
import org.grizz.model.User;
import org.grizz.model.enummerations.BuildingType;
import org.grizz.model.repos.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private UserService userService;

    public Planet get(String id) {
        return planetRepository.findById(id);
    }

    public Planet create(User owner) {
        Planet planet = Planet.builder()
                .buildings(createDefaultBuildings())
                .owner(owner.getLogin())
                .build();
        Planet createdPlanet = planetRepository.save(planet);

        return createdPlanet;
    }

    private Set<Building> createDefaultBuildings() {
        Set<Building> buildings = Lists.newArrayList(BuildingType.values()).stream()
                .map(type -> Building.builder()
                        .level(type.getDefaultLevel())
                        .type(type)
                        .build())
                .collect(Collectors.toSet());
        return buildings;
    }

    public List<Planet> getCurrentUserPlanets() {
        String owner = userService.getCurrentUserLogin();
        return planetRepository.findByOwner(owner);
    }
}
