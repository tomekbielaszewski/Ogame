package org.grizz.model.repos;

import org.grizz.model.Building;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuildingRepository extends MongoRepository<Building, String> {

    Building findByType(String name);

}
