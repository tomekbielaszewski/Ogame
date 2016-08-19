package org.grizz.exception;

import org.grizz.i18n.Localization;

public class UserBadPasswordException extends RuntimeException {
    public UserBadPasswordException() {
        super(Localization.EXCEPTION_BAD_PASSWORD);
    }
}
