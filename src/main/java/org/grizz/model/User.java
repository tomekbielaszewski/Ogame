package org.grizz.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class User {
    private String login;
    private String passwordHash;
    private Set<String> roles;
}
