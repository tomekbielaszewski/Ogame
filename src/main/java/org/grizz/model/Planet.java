package org.grizz.model;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@Document(collection = "planets")
public class Planet {

    @Id
    private String id;
    private Set<Building> buildings = Sets.newHashSet();

}
