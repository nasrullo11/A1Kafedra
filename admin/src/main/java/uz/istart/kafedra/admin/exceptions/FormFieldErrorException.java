package uz.istart.kafedra.admin.exceptions;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class FormFieldErrorException extends RuntimeException {
    private String message;
    private List<FieldError> fieldError;

    public FormFieldErrorException(List<FieldError> fieldError) {
        this(null,fieldError);
    }

    public FormFieldErrorException(String message,List<FieldError> fieldError) {
        this.message = message;
        this.fieldError = fieldError;
    }
}
