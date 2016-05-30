package org.grizz.service;

import org.grizz.model.Planet;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {
    public Planet get(String id) {
        return Planet.builder().id(id).build();
    }
}
