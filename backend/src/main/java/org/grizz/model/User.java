package org.grizz.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import org.grizz.web.api.views.View;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@Document(collection = "users")
public class User {
    public static final transient String PLAYER_ROLE = "PLAYER";
    public static final transient String ADMIN_ROLE = "ADMIN";

    @Id
    @JsonView(View.Summary.class)
    private String id;

    @JsonView(View.Summary.class)
    @Indexed(unique = true)
    private String login;

    @JsonView(View.Summary.class)
    private Set<String> roles;

    private String passwordHash;
}
