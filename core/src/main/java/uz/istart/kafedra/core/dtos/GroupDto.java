package uz.istart.kafedra.core.dtos;

import uz.istart.kafedra.core.validations.user.CheckUniqueUsername;
import com.fasterxml.jackson.annotation.JsonInclude;
import uz.istart.kafedra.core.constants.Role;
import lombok.Getter;
import lombok.Setter;
import uz.istart.kafedra.core.validations.user.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class GroupDto implements Serializable{

    private Long id;

    @NotEmpty
    private String groupName;

    private Integer entranceYear;

    private EducationTypeDto groupEducationType;
    private FieldDto groupField;
    private Integer amountStudents;
}
