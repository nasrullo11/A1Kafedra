package uz.istart.kafedra.core.dtos;

import lombok.Getter;
import lombok.Setter;
import uz.istart.kafedra.core.validations.user.CheckUniqueUsername;

import java.io.Serializable;

@Getter
@Setter
@CheckUniqueUsername
public class LessonTypeDto implements Serializable{

    private Long id;
    private String lessonTypeName;
}
