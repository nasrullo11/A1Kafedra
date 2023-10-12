package uz.istart.kafedra.core.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.LoadEntity;

import java.util.List;
import java.util.Optional;

public interface LoadRepository extends JpaRepository<LoadEntity,Long>, JpaSpecificationExecutor<LoadEntity> {
    Optional<List<LoadEntity>> findByFieldId(Integer fieldId);
    Page<LoadEntity> findBySubjectId(Long subjectId, Pageable pageable);
}
