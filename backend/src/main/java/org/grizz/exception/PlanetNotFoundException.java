package org.grizz.exception;

import lombok.Value;
import org.grizz.i18n.Localization;

@Value
public class PlanetNotFoundException extends RuntimeException {
    private final String id;

    public PlanetNotFoundException(String id) {
        super(Localization.EXCEPTION_PLANET_NOT_FOUND);
        this.id = id;
    }
}
