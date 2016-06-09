package org.grizz.service;

import org.grizz.model.Planet;
import org.grizz.model.repos.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    public Planet get(String id) {
        return planetRepository.findById(id);
    }

}
