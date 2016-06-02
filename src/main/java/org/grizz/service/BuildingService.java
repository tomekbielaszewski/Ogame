package org.grizz.service;

import org.grizz.model.Building;
import org.grizz.model.repos.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;

    public Building getByType(String type) {
        return buildingRepository.findByType(type);
    }
}
