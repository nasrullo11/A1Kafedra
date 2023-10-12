package uz.istart.kafedra.core.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class LoadDto implements Serializable{

    private Long id;

    private FieldDto loadField;

    private SubjectDto loadSubject;

    private Integer subjectHour;

    private Integer semester;
}
