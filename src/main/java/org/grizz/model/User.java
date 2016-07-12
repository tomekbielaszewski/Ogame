package org.grizz.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Indexed(unique = true)
    @Size(min = 8, max = 30)
    private String login;
    @Min(8)
    private String passwordHash;
    private Set<String> roles;
    private int maxPlanetNumber;
    private int planetNumber;

}
