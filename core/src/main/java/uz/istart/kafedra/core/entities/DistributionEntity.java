package uz.istart.kafedra.core.entities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.*;
import uz.istart.kafedra.core.entities.base.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = TableNames.distributions)
public class DistributionEntity extends BaseEntity{

    @Column(name = "userId")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false,updatable = false)
    private UserEntity distributionUser;

    @Column(name = "subjectId")
    private Long subjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId",insertable = false,updatable = false)
    private SubjectEntity distributionSubject;

    @Column(name = "distributionHour")
    private Integer subjectHour;

    @Column(name = "groupId")
    private Long groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId",insertable = false,updatable = false)
    private GroupEntity distributionGroup;

    @Column(name = "lessonTypeId")
    private Long lessonTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonTypeId",insertable = false,updatable = false)
    private LessonTypeEntity distributionLessonType;

    @Column(name = "departmentId")
    private Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId",insertable = false,updatable = false)
    private DepartmentEntity distributionDepartment;

    public DistributionDto getDto(){
        DistributionDto distributionDto = new DistributionDto();
        BeanUtils.copyProperties(this,distributionDto);
        if (getDistributionUser() != null){
            UserDto user = new UserDto();
            user.setId(getDistributionUser().getId());
            user.setUsername(getDistributionUser().getUsername());
            user.setFullName(getDistributionUser().getFullName());
            distributionDto.setDistributionUser(user);
        }

        if (getDistributionSubject() != null){
            SubjectDto subject = new SubjectDto();
            subject.setId(getDistributionSubject().getId());
            subject.setSubjectName(getDistributionSubject().getSubjectName());
            distributionDto.setDistributionSubject(subject);
        }

        if (getDistributionGroup() != null){
            GroupDto group = new GroupDto();
            group.setId(getDistributionGroup().getId());
            group.setGroupName(getDistributionGroup().getGroupName());
            group.setEntranceYear(getDistributionGroup().getEntranceYear());
            distributionDto.setDistributionGroup(group);
        }

        if (getDistributionGroup() != null){
            LessonTypeDto lType = new LessonTypeDto();
            lType.setId(getDistributionLessonType().getId());
            lType.setLessonTypeName(getDistributionLessonType().getLessonTypeName());
            distributionDto.setDistributionLessonType(lType);
        }

        if (getDistributionGroup() != null){
            DepartmentDto dep = new DepartmentDto();
            dep.setId(getDistributionDepartment().getId());
            dep.setDepartmentName(getDistributionDepartment().getDepartmentName());
            distributionDto.setDistributionDepartment(dep);
        }

        return distributionDto;
    }

}
