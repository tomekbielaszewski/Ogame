package org.grizz.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Building {

    private String type;
    private int level;

}
