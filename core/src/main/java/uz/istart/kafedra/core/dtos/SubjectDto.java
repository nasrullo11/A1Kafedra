package uz.istart.kafedra.core.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class SubjectDto implements Serializable{

    private Long id;

    @NotEmpty
    private String subjectName;

    private Integer subjectBall;

    private DepartmentDto subjectDepartment;

    private Integer totalHour;
}
