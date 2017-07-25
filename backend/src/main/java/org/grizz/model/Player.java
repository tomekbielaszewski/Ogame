package org.grizz.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@Document(collection = "players")
public class Player {
  public static final transient String PLAYER_ROLE = "PLAYER";
  public static final transient String ADMIN_ROLE = "ADMIN";

  @Id
  private String id;

  @Indexed(unique = true)
  private String login;

  private Set<String> roles;
}
