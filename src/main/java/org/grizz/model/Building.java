package org.grizz.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
public class Building {

    private String type;
    private int level;

}
