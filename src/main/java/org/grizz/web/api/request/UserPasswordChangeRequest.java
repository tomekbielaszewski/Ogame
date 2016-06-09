package org.grizz.web.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
}
