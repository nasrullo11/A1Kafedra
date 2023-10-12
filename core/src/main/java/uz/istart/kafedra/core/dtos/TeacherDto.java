package uz.istart.kafedra.core.dtos;

import uz.istart.kafedra.core.validations.user.CheckUniqueTeacherUsername;
import com.fasterxml.jackson.annotation.JsonInclude;
import uz.istart.kafedra.core.constants.Role;
import lombok.Getter;
import lombok.Setter;
import uz.istart.kafedra.core.validations.user.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@CheckUniqueTeacherUsername
@JsonInclude(NON_NULL)
public class TeacherDto implements Serializable{

    private Long id;

    private String token;

    @NotEmpty
    private String teacherName;

    @NotEmpty
    private String teacherSurname;

    private String teacherSecondName;

    @NotEmpty(message = "Пожалуйста, введите аккоунт название пользователя")
    private String teacherUsername;

    @ValidPassword
    private String teacherPassword;

    @ValidPassword
    private String teacherNewPassword;

    private DepartmentDto teacherDepartment;
    private FileDto teacherFileLogo;

}
