package org.grizz.exception;

import org.grizz.i18n.Localization;
import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException {
    public UserAuthenticationException() {
        super(Localization.EXCEPTION_AUTHENTICATION_FAILED);
    }
}
