package uz.istart.kafedra.admin.form.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FormFieldErrors implements Serializable {
    private String message;
    private List<FormFieldError> formFieldErrors;
}
