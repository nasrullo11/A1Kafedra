package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.WorkTypeDto;
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
@Table(name = TableNames.workTypes)
public class WorkTypeEntity extends BaseEntity{

    @Column(name = "workTypeName")
    private String workTypeName;

    public WorkTypeDto getDto(){
        WorkTypeDto workTypeDto = new WorkTypeDto();
        BeanUtils.copyProperties(this,workTypeDto);

        return workTypeDto;
    }

}
