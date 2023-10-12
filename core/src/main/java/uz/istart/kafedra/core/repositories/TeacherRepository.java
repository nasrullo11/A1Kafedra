package uz.istart.kafedra.core.repositories;

import uz.istart.kafedra.core.entities.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long>, JpaSpecificationExecutor<TeacherEntity> {
    Optional<TeacherEntity> findByTeacherUsernameIgnoreCase(String teacherUsername);

}
