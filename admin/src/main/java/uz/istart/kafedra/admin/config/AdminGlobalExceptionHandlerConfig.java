package uz.istart.kafedra.admin.config;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.istart.kafedra.admin.exceptions.FormFieldErrorException;
import uz.istart.kafedra.admin.form.error.FormFieldError;
import uz.istart.kafedra.admin.form.error.FormFieldErrors;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(basePackages = "uz.istart.kafedra.admin.controllers")
public class AdminGlobalExceptionHandlerConfig {

    @ExceptionHandler(FormFieldErrorException.class)
    public ResponseEntity<FormFieldErrors> throwsFormValidationException(FormFieldErrorException ex) {
        List<FormFieldError> formFieldErrors = new ArrayList<>();
        for (FieldError fieldError : ex.getFieldError()) {
            FormFieldError formFieldError = new FormFieldError(fieldError.getField(), fieldError.getDefaultMessage());
            formFieldErrors.add(formFieldError);
        }
        return ResponseEntity.status(912)
                .body(new FormFieldErrors(ex.getMessage(), formFieldErrors));
    }

}
