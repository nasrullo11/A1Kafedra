package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.FieldDto;
import uz.istart.kafedra.core.constants.Role;
import uz.istart.kafedra.core.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = TableNames.fields)
public class FieldEntity extends BaseEntity{

    @Column(name = "fieldName")
    private String fieldName;

    @Column(name = "fieldCode")
    private String fieldCode;

    public FieldDto getDto(){
        FieldDto fieldDto = new FieldDto();
        BeanUtils.copyProperties(this,fieldDto);
        return fieldDto;
    }

    public List<FieldDto> getList(){
        List<FieldDto> dtoList = new ArrayList<>();
        BeanUtils.copyProperties(this, dtoList);
        return dtoList;
    }
}
