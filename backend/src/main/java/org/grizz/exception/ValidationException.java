package org.grizz.exception;

import lombok.Value;
import org.grizz.i18n.Localization;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@Value
public class ValidationException extends RuntimeException {
    private final List<ObjectError> allErrors;

    public ValidationException(MethodArgumentNotValidException cause) {
        super(Localization.EXCEPTION_VALIDATION);
        this.allErrors = cause.getBindingResult().getAllErrors();
    }
}
