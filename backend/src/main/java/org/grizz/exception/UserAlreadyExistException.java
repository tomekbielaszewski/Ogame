package org.grizz.exception;

import lombok.Value;
import org.grizz.i18n.Localization;

@Value
public class UserAlreadyExistException extends RuntimeException {
    private final String login;

    public UserAlreadyExistException(String login) {
        super(Localization.EXCEPTION_USER_ALREADY_EXIST);
        this.login = login;
    }
}
