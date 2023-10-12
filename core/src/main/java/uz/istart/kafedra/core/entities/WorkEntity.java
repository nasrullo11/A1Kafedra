package uz.istart.kafedra.core.entities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.dtos.WorkDto;
import uz.istart.kafedra.core.dtos.WorkStatusDto;
import uz.istart.kafedra.core.dtos.WorkTypeDto;
import uz.istart.kafedra.core.entities.base.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = TableNames.works)
public class WorkEntity extends BaseEntity{

    @Column(name = "userId")
    private Long teacherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false,updatable = false)
    private UserEntity workTeacher;

    @Column(name = "workTitle")
    private String workTitle;

    @Column(name = "work_student")
    private String workStudent;

    @Column(name = "workTypeId")
    private Long workTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workTypeId",insertable = false,updatable = false)
    private WorkTypeEntity workType;

    @Column(name = "workDeadline")
    private String workDeadline;

    @Column(name = "workDoiLink")
    private String workDoiLink;

    @Column(name = "workStatusId")
    private Long workStatusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workStatusId",insertable = false,updatable = false)
    private WorkStatusEntity workStatus;

    @Column(name = "workDescription")
    private String workDescription;

    public WorkDto getDto(){
        WorkDto workDto = new WorkDto();
        BeanUtils.copyProperties(this,workDto);
        if (getWorkTeacher() != null){
            UserDto teacher = new UserDto();
            teacher.setId(getWorkTeacher().getId());
            teacher.setUsername(getWorkTeacher().getUsername());
            teacher.setFullName(getWorkTeacher().getFullName());
            workDto.setWorkTeacher(teacher);
        }

        if (getWorkType() != null){
            WorkTypeDto workType = new WorkTypeDto();
            workType.setId(getWorkType().getId());
            workType.setWorkTypeName(getWorkType().getWorkTypeName());
            workDto.setWorkType(workType);
        }

        if (getWorkStatus() != null){
            WorkStatusDto workStatus = new WorkStatusDto();
            workStatus.setId(getWorkStatus().getId());
            workStatus.setWorkStatusName(getWorkStatus().getWorkStatusName());
            workDto.setWorkStatus(workStatus);
        }
        return workDto;
    }

}
