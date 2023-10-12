package uz.istart.kafedra.core.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class DistributionDto implements Serializable{

    private Long id;

    private UserDto distributionUser;

    private SubjectDto distributionSubject;

    private Integer subjectHour;
    private Integer controlHour;

    private GroupDto distributionGroup;

    private LessonTypeDto distributionLessonType;

    private DepartmentDto distributionDepartment;
}
