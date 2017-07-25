package org.grizz.exception;

import org.grizz.i18n.Localization;

public class PlayerBadPasswordException extends RuntimeException {
    public PlayerBadPasswordException() {
        super(Localization.EXCEPTION_BAD_PASSWORD);
    }
}
