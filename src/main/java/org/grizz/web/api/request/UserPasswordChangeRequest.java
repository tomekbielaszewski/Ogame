package org.grizz.web.api.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserPasswordChangeRequest {
    public UserPasswordChangeRequest() {}

    private String oldPassword;
    private String newPassword;
}
