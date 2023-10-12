package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.WeekdayDto;
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
@Table(name = TableNames.weekdays)
public class WeekdayEntity extends BaseEntity{

    @Column(name = "weekdayName")
    private String weekdayName;

    public WeekdayDto getDto(){
        WeekdayDto weekdayDto = new WeekdayDto();
        BeanUtils.copyProperties(this,weekdayDto);

        return weekdayDto;
    }

}
