package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.istart.kafedra.core.dtos.SubjectDto;
import uz.istart.kafedra.core.entities.SubjectEntity;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long>, JpaSpecificationExecutor<SubjectEntity> {
    Optional<SubjectEntity> findBySubjectNameIgnoreCase(String subjectName);
    List<SubjectEntity> findByDepartmentId(Long departmentId);

    @Query(value = "SELECT * FROM kafedra_subjects", nativeQuery = true)
    List<SubjectDto> getList();

}
