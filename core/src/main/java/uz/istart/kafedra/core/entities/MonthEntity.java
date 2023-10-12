package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.MonthDto;
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
@Table(name = TableNames.months)
public class MonthEntity extends BaseEntity {

    @Column(name = "monthName")
    private String monthName;

    public MonthDto getDto(){
        MonthDto monthDto = new MonthDto();
        BeanUtils.copyProperties(this,monthDto);

        return monthDto;
    }

}
