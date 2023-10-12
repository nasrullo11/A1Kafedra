package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.EducationTypeDto;
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
@Table(name = TableNames.educationTypes)

public class EducationTypeEntity extends BaseEntity{

    @Column(name = "educationTypeName")
    private String educationTypeName;

    public EducationTypeDto getDto(){
        EducationTypeDto educationTypeDto = new EducationTypeDto();
        BeanUtils.copyProperties(this,educationTypeDto);

        return educationTypeDto;
    }
}
