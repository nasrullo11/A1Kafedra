package uz.istart.kafedra.core.validations.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckUniqueTeacherUsernameValidator.class)
@Documented
@Repeatable(CheckUniqueTeacherUsername.List.class)
public @interface CheckUniqueTeacherUsername {
    String message() default "Такой Е-майл с пользователь уже существует в системе!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CheckUniqueTeacherUsername[] value();
    }
}
