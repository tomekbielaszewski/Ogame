package org.grizz.model.repos;

import org.grizz.model.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanetRepository extends MongoRepository<Planet, String> {

    Planet findById(String id);

}
