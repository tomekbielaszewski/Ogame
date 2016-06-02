package org.grizz.web.api;

import org.grizz.model.Building;
import org.grizz.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;


    @RequestMapping(value = "/{type}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Building getBuilding(@PathVariable String type) {
        return buildingService.getByType(type);
    }
}
