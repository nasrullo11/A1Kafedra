package uz.istart.kafedra.core.entities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.istart.kafedra.core.constants.Role;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.DepartmentDto;
import uz.istart.kafedra.core.dtos.FileDto;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.entities.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = TableNames.users)
public class UserEntity extends BaseEntity {

    @Column(name = "token")
    private String token;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "create_date",columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "file_logo_id")
    private Long fileLogoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_logo_id",insertable = false,updatable = false)
    private FileEntity fileLogo;

    @Column(name = "department_id")
    private Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",insertable = false,updatable = false)
    private DepartmentEntity department;

    public UserDto getDto(){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(this,userDto,"password");
        if (getFileLogo() != null){
            FileDto file = new FileDto();
            file.setId(getFileLogo().getId());
            file.setOrgFileName(getFileLogo().getOrgFileName());
            file.setPath(getFileLogo().getPath());
            file.setFileName(getFileLogo().getFileName());
            userDto.setFileLogo(file);
        }
        if (getDepartment() != null){
            DepartmentDto dep = new DepartmentDto();
            dep.setId(getDepartment().getId());
            dep.setDepartmentName(getDepartment().getDepartmentName());
            userDto.setDepartment(dep);
        }
        return userDto;
    }
}
