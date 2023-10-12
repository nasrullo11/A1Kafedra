package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.WorkStatusDto;
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
@Table(name = TableNames.workStatus)
public class WorkStatusEntity extends BaseEntity{

    @Column(name = "workStatusName")
    private String workStatusName;

    public WorkStatusDto getDto(){
        WorkStatusDto workStatusDto = new WorkStatusDto();
        BeanUtils.copyProperties(this,workStatusDto);

        return workStatusDto;
    }
}
