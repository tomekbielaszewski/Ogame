package org.grizz.web.api.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ExceptionResponse {
    public static ExceptionResponse of(String message, Object cause) {
        return new ExceptionResponse(message, cause);
    }

    public static ExceptionResponse of(String message) {
        return ExceptionResponse.of(message, null);
    }

    public ExceptionResponse() {
    }

    private String message;
    private Object cause;
}
