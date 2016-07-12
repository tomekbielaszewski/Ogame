package org.grizz.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.grizz.model.enummerations.BuildingType;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Building {

    private BuildingType type;
    private int level;

}
