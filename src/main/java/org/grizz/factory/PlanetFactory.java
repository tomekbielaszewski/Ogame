package org.grizz.factory;

import com.google.common.collect.Lists;
import org.grizz.model.Building;
import org.grizz.model.Planet;
import org.grizz.model.User;
import org.grizz.model.enummerations.BuildingType;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlanetFactory {


    private static String DEFAULT_NAME = "Planet Mother";

    public Planet createDefaultPlanet(User owner) {
        Set<Building> buildings = createDefaultBuildingList();
        return Planet.builder().name(DEFAULT_NAME).buildings(buildings).ownerLogin(owner.getLogin()).build();
    }

    private Set<Building> createDefaultBuildingList() {
        return Lists.newArrayList(BuildingType.values()).stream().map(type ->
                new Building(type, type.getLevel())).collect(Collectors.toSet());
    }
}
