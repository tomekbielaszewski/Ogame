package org.grizz.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.grizz.exception.PlanetNotFoundException;
import org.grizz.model.Planet;
import org.grizz.model.Player;
import org.grizz.model.enummerations.BuildingType;
import org.grizz.model.repos.PlanetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlanetServiceTest {
    private static final String ID = "id";
    private static final String OWNER = "owner";

    @Mock
    private PlanetRepository planetRepository;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlanetService planetService;

    @Test
    public void shouldReturnPlanetWhenGettingById() {
        when(planetRepository.findById(ID)).thenReturn(Optional.of(dummyPlanet()));

        planetService.get(ID);

        verify(planetRepository).findById(ID);
    }

    @Test
    public void shouldNewlyCreatedPlanetHaveOwner() {
        Player owner = Player.builder().login(OWNER).build();
        when(planetRepository.save(any(Planet.class))).then(returnsFirstArg());

        Planet newPlanet = planetService.create(owner);

        assertThat(newPlanet.getOwner(), equalTo(OWNER));
        verify(planetRepository).save(any(Planet.class));
    }

    @Test
    public void shouldNewlyCreatedPlanetHaveDefaultBuildings() {
        Player owner = Player.builder().build();
        when(planetRepository.save(any(Planet.class))).then(returnsFirstArg());

        Planet newPlanet = planetService.create(owner);

        assertThat(newPlanet.getBuildings(), hasSize(BuildingType.values().length));
        verify(planetRepository).save(any(Planet.class));
    }

    @Test
    public void shouldReturnPlanetsBelongingToUser() {
        when(playerService.getCurrentPlayerLogin()).thenReturn(OWNER);
        when(planetRepository.findByOwner(OWNER)).thenReturn(Lists.newArrayList(dummyPlanet()));

        List<Planet> planets = planetService.getCurrentUserPlanets();

        assertThat(planets, hasSize(1));
        assertThat(planets.get(0).getOwner(), equalTo(OWNER));
        verify(planetRepository).findByOwner(OWNER);
    }

    @Test(expected = PlanetNotFoundException.class)
    public void shouldThrowExceptionWhenPlanetNotFound() {
        when(planetRepository.findById(ID)).thenReturn(Optional.empty());
        planetService.get(ID);
    }

    private Planet dummyPlanet() {
        return Planet.builder()
                .id(ID)
                .owner(OWNER)
                .buildings(Sets.newHashSet())
                .build();
    }
}