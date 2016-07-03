package org.grizz.sampleData;

import com.google.common.collect.Sets;
import org.grizz.OgameCloneApplication;
import org.grizz.model.Building;
import org.grizz.model.Planet;
import org.grizz.model.User;
import org.grizz.model.repos.PlanetRepository;
import org.grizz.model.repos.UserRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OgameCloneApplication.class)
@WebAppConfiguration
public class SampleData {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private UserRepository userRepository;

    @Ignore
    @Test
    public void fillPlanets() {
        planetRepository.deleteAll();
        Set<Building> buildings = new HashSet<>();
        buildings.add(Building.builder().level(1).type("Metal mine").build());
        buildings.add(Building.builder().level(1).type("Crystal mine").build());
        buildings.add(Building.builder().level(1).type("Deuterium mine").build());

        planetRepository.insert(Planet.builder().id("Sample planet").buildings(buildings).build());
    }

    @Ignore
    @Test
    public void fillUsers() {
        userRepository.deleteAll();

        User user = User.builder().login("test_user").passwordHash(BCrypt.hashpw("test_pass", BCrypt.gensalt())).roles(Sets.newHashSet()).build();

        userRepository.insert(user);
    }

}
