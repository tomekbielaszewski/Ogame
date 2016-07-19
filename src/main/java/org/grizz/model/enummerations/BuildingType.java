package org.grizz.model.enummerations;

import lombok.Getter;

public enum BuildingType {
    METAL_MINE(1),
    CRYSTAL_MINE(1),
    EXTRACTOR_DEUTERIUM(0),
    SOLAR_POWER(1),
    FUSION_REACTOR(0),
    ROBOTICS_FACTORY(0),
    NANITE_FACTORY(0),
    SHIPYARD(0),
    MAGAZINE_METALS(0),
    CRYSTAL_STORAGE(0),
    THE_TANK_OF_A_DEUTERIUM(0),
    RESEARCH_LABORATORY(0),
    TERRAFORMER(0),
    DEPOSIT_ALLIANCE(0),
    MISSILE_SILO(0);

    @Getter
    private final int defaultLevel;

    BuildingType(int defaultLevel) {
        this.defaultLevel = defaultLevel;
    }
}
