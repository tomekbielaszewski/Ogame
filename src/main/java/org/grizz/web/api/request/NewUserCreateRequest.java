package org.grizz.web.api.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class NewUserCreateRequest {
    @Size(min = 5, max = 15)
    private String login;
    @Size(min = 8)
    private String password;

    public NewUserCreateRequest() {
    }

}
