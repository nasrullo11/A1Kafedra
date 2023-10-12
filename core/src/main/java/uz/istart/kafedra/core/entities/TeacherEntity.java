package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.DepartmentDto;
import uz.istart.kafedra.core.dtos.TeacherDto;
import uz.istart.kafedra.core.dtos.FileDto;
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
@Table(name = TableNames.teachers)
public class TeacherEntity extends BaseEntity{

    @Column(name = "token")
    private String token;

    @Column(name = "teacherName")
    private String teacherName;

    @Column(name = "teacherSurname")
    private String teacherSurname;

    @Column(name = "teacherSecondName")
    private String teacherSecondName;

    @Column(name = "teacherUsername", unique = true, nullable = false)
    private String teacherUsername;

    @Column(name = "teacherPassword", nullable = false)
    private String teacherPassword;

    @Column(name = "departmentId")
    private Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentId",insertable = false,updatable = false)
    private DepartmentEntity teacherDepartment;

    @Column(name = "teacherFileLogoId")
    private Long teacherFileLogoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherFileLogoId",insertable = false,updatable = false)
    private FileEntity teacherFileLogo;

    public TeacherDto getDto(){
        TeacherDto teacherDto = new TeacherDto();
        BeanUtils.copyProperties(this,teacherDto,"teacherPassword");
        if (getTeacherDepartment() != null){
            DepartmentDto department = new DepartmentDto();
            department.setId(getTeacherDepartment().getId());
            department.setDepartmentName(getTeacherDepartment().getDepartmentName());
            teacherDto.setTeacherDepartment(department);
        }

        if (getTeacherFileLogo() != null){
            FileDto file = new FileDto();
            file.setId(getTeacherFileLogo().getId());
            file.setOrgFileName(getTeacherFileLogo().getOrgFileName());
            file.setPath(getTeacherFileLogo().getPath());
            file.setFileName(getTeacherFileLogo().getFileName());
            teacherDto.setTeacherFileLogo(file);
        }

        return teacherDto;
    }

}
