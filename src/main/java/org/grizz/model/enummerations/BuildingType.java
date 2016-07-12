package org.grizz.model.enummerations;

public enum BuildingType {

    METAL_MINE(1),
    CRYSTAL_MINE(1),
    EXTRACTOR_DEUTERIUM(1),
    SOLAR_POWER(1),
    FUSION_REACTOR(0),
    ROBOTICS_FACTORY(0),
    NANITE_FACTORY(0),
    SHIPYARD(0),
    MAGAZINE_METALS(0),
    CRYSTAL_STORAGE(0),
    THE_TANK_OF_A_DEUTERIUM(0),
    RESEARCH_LABORATORY(0),
    TERRAFORM(0),
    DEPOSIT_ALLIANCE(0),
    MISSILE_SILO(0);

    private int level;

    BuildingType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
