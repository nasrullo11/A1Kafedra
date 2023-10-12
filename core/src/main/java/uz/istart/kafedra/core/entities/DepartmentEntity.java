package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.DepartmentDto;
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
@Table(name = TableNames.departments)
public class DepartmentEntity extends BaseEntity{

    @Column(name = "departmentName")
    private String departmentName;

    public DepartmentDto getDto(){
        DepartmentDto departmentDto = new DepartmentDto();
        BeanUtils.copyProperties(this,departmentDto);

        return departmentDto;
    }

}
