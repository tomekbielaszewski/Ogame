package org.grizz.service;

import org.grizz.model.Planet;

public class PlanetService {
    public Planet get(String id) {
        return Planet.builder().id(id).build();
    }
}
