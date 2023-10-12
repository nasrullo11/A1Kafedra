package uz.istart.kafedra.core.entities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.DepartmentDto;
import uz.istart.kafedra.core.dtos.SubjectDto;
import uz.istart.kafedra.core.entities.base.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = TableNames.subjects)
public class SubjectEntity extends BaseEntity{

    @Column(name = "subjectName")
    private String subjectName;

    @Column(name = "subjectBall")
    private Integer subjectBall;

    @Column(name = "departmentId")
    private Long departmentId;

    @Column(name = "totalHour", columnDefinition = "integer default 0")
    private Integer totalHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId",insertable = false,updatable = false)
    private DepartmentEntity subjectDepartment;

    public SubjectDto getDto(){
        SubjectDto subjectDto = new SubjectDto();
        BeanUtils.copyProperties(this,subjectDto);
        if (getSubjectDepartment() != null){
            DepartmentDto department = new DepartmentDto();
            department.setId(getSubjectDepartment().getId());
            department.setDepartmentName(getSubjectDepartment().getDepartmentName());
            subjectDto.setSubjectDepartment(department);
        }
        return subjectDto;
    }
}
