package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.LessonTypeEntity;

import java.util.List;
import java.util.Optional;

public interface LessonTypeRepository extends JpaRepository<LessonTypeEntity,Long>, JpaSpecificationExecutor<LessonTypeEntity> {
    Optional<List<LessonTypeEntity>> findByLessonTypeNameIgnoreCase(String lessonTypeName);
}
