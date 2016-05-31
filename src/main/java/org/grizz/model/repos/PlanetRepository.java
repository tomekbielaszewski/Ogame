package org.grizz.model.repos;

import org.grizz.model.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by bartek on 31.05.2016.
 */
public interface PlanetRepository extends MongoRepository<Planet, String> {

    Planet findById(String id);


}
