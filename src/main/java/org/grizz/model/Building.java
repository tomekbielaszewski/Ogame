package org.grizz.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "buildings")
public class Building {

    private String type;
    private int level;

}
