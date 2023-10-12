package uz.istart.kafedra.admin.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.istart.kafedra.core.entities.TeacherEntity;
import uz.istart.kafedra.core.repositories.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminTeacherDetailsService implements UserDetailsService{

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String teacherUsername) throws UsernameNotFoundException {
        Optional<TeacherEntity> teacherEntity = teacherRepository.findByTeacherUsernameIgnoreCase(teacherUsername);
        if (!teacherEntity.isPresent()) {
            throw new UsernameNotFoundException(teacherUsername + " teacher not found!");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(teacherEntity.get().getTeacherUsername()));

        return new AdminTeacherDetails(
                teacherEntity.get().getTeacherUsername(),
                teacherEntity.get().getTeacherPassword(),
                teacherEntity.get().getId(),
                teacherEntity.get().getTeacherName(),
                teacherEntity.get().getTeacherSurname(),
                grantedAuthorities);
    }
}
