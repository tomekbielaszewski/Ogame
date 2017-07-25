package org.grizz.model.repos;

import org.grizz.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String> {

    Player findByLogin(String login);

}
