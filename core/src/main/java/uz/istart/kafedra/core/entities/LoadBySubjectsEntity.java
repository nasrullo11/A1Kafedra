package uz.istart.kafedra.core.entities;

import lombok.Getter;
import lombok.Setter;
import uz.istart.kafedra.core.dtos.SubjectDto;

@Getter
@Setter
public class LoadBySubjectsEntity {

    private Long subjectId;

    private Integer totalHour;

    private SubjectDto loadBySubject;


}
