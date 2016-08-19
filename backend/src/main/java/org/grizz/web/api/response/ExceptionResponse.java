package org.grizz.web.api.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ExceptionResponse {
    public ExceptionResponse() {
    }

    private String message;
    private Object cause;
}
