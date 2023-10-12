package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.istart.kafedra.core.dtos.LoadBySubjectsDto;
import uz.istart.kafedra.core.entities.LoadEntity;

import java.util.List;

@Repository
public interface LoadBySubjectsRepository extends JpaRepository<LoadEntity,Long>{

    @Query(value = "SELECT l.subject_id AS subjectId, ks.subject_name AS subjectName, SUM(l.load_hour) AS totalHour FROM kafedra_loads AS l LEFT JOIN kafedra_subjects ks ON l.subject_id = ks.id GROUP BY subject_id, subject_name", nativeQuery = true)
    List<LoadBySubjectsDto> getLoadBySubjectId();
}
