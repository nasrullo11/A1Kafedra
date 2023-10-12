package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.LessonTypeDto;
import uz.istart.kafedra.core.constants.Role;
import uz.istart.kafedra.core.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = TableNames.lessonTypes)
public class LessonTypeEntity extends BaseEntity{

    @Column(name = "lessonTypeName")
    private String lessonTypeName;

    public LessonTypeDto getDto(){
        LessonTypeDto lessonTypeDto = new LessonTypeDto();
        BeanUtils.copyProperties(this,lessonTypeDto);

        return lessonTypeDto;
    }

}
