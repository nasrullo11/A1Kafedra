package uz.istart.kafedra.admin.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import uz.istart.kafedra.core.constants.Role;

import java.util.Collection;

@Getter
public class AdminTeacherDetails extends User{

    private Long teacherId;
    private String teacherName;
    private String teacherUsername;
    private String teacherSurname;

    public AdminTeacherDetails(String teacherUsername, String password, Long teacherId, String teacherName,  String teacherSurname, Collection<? extends GrantedAuthority> authorities) {
        super(teacherUsername, password, authorities);
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherSurname = teacherSurname;
    }
}
