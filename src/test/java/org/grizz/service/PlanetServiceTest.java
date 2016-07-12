package org.grizz.service;

import lombok.extern.slf4j.Slf4j;
import org.grizz.OgameCloneApplication;
import org.grizz.config.security.SecurityConfig;
import org.grizz.exception.MaxNumberOfPlanetException;
import org.grizz.model.Planet;
import org.grizz.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OgameCloneApplication.class, SecurityConfig.class})
public class PlanetServiceTest {


    @Autowired
    @InjectMocks
    private PlanetService planetService;

    @Autowired
    @InjectMocks
    private UserService userService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreatePlanetWhenOwnerHaveLessPlanetsThanMax() {
        User user = dummyUser();
        Planet planet = planetService.createPlanet(user);
        log.debug("New planet : " + planet);
        assertThat(planet.getOwnerLogin(), equalTo(user.getLogin()));
        assertThat(user.getPlanetNumber(), equalTo(1));
    }

    @Test(expected = MaxNumberOfPlanetException.class)
    public void shouldThrowExceptionWhenOwnerExceedsMaxNumberOfPlanet() {
        User user = dummyUser();
        user.setMaxPlanetNumber(0);
        planetService.createPlanet(user);
    }


    private User dummyUser() {
        return User.builder().login("dummyUser4").passwordHash("$2a$10$.ewhODmJsLY6b3hntVLilujdm3mG3vvRKvF1OEx4HWn5/6Bi7fe2C")
                .planetNumber(0).maxPlanetNumber(8).build();

    }

}

