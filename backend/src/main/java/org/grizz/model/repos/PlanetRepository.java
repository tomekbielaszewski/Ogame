package org.grizz.model.repos;

import org.grizz.model.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlanetRepository extends MongoRepository<Planet, String> {

    Optional<Planet> findById(String id);
    List<Planet> findByOwner(String owner);

}
