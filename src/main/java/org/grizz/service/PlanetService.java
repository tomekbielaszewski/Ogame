package org.grizz.service;

import lombok.extern.slf4j.Slf4j;
import org.grizz.exception.MaxNumberOfPlanetException;
import org.grizz.factory.PlanetFactory;
import org.grizz.model.Planet;
import org.grizz.model.User;
import org.grizz.model.repos.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private PlanetFactory planetFactory;
    @Autowired
    private UserService userService;

    public Planet createPlanet(User planetOwner) throws MaxNumberOfPlanetException {
        log.debug("Creating planet for user : " + planetOwner);
        if (userService.canHaveMorePlanet(planetOwner)) {
            userService.increaseNumberOfPlanetsAndSave(planetOwner);
            return planetRepository.insert(planetFactory.createDefaultPlanet(planetOwner));
        } else {
            throw new MaxNumberOfPlanetException();
        }
    }

    public Planet get(String id) {
        return planetRepository.findById(id);
    }

}
