package org.grizz.model;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Builder
@Document(collection = "planets")
public class Planet {

    private String id;
    private Set<Building> buildings = new HashSet<>();

}
