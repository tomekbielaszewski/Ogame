package org.grizz.model;

import lombok.Builder;
import lombok.Data;
import org.grizz.model.enummerations.BuildingType;

@Data
@Builder
public class Building {

    private BuildingType type;
    private int level;

}
