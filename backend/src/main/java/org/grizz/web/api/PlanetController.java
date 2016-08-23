package org.grizz.web.api;

import org.grizz.exception.PlanetNotFoundException;
import org.grizz.model.Planet;
import org.grizz.service.PlanetService;
import org.grizz.web.api.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Planet getPlanet(@PathVariable String id) {
        return planetService.get(id);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Planet> getPlanets() {
        return planetService.getCurrentUserPlanets();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PlanetNotFoundException.class)
    public ExceptionResponse handlePlanetNotFound(PlanetNotFoundException e) {
        return ExceptionResponse.builder()
                .message(e.getMessage())
                .cause(e.getId())
                .build();
    }
}
