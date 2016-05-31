package org.grizz.model.repos;

import org.grizz.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by bartek on 31.05.2016.
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByLogin(String login);
}
