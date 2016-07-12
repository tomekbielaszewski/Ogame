package org.grizz.web.api.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.grizz.i18n.Localization;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserCreateRequest {
    public UserCreateRequest() {}

    @NotNull(message = Localization.VALIDATION_USER_LOGIN_NOTNULL)
    @Size(min = 3, max = 25, message = Localization.VALIDATION_USER_LOGIN_SIZE)
    private String login;

    @NotNull(message = Localization.VALIDATION_USER_PASSWORD_NOTNULL)
    @Size(min = 8, max = 255, message = Localization.VALIDATION_USER_PASSWORD_SIZE)
    private String password;
}
