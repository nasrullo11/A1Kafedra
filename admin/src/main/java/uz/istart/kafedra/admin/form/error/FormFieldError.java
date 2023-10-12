package uz.istart.kafedra.admin.form.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormFieldError {
    private String field;
    private String message;
}
