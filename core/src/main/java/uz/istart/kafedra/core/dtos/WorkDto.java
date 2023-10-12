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
public class WorkDto implements Serializable{

    private Long id;

    private UserDto workTeacher;
    private String workStudent;

    @NotEmpty
    private String workTitle;

    private WorkTypeDto workType;

    @NotEmpty
    private String workDeadline;

    private String workDoiLink;

    private WorkStatusDto workStatus;

    private String workDescription;
}
