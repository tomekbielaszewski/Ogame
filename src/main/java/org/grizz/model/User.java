package org.grizz.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@Document(collection = "users")
public class User {


    private String login;
    private String passwordHash;
    private Set<String> roles;
}
