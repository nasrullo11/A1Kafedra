package uz.istart.kafedra.core.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.WorkEntity;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<WorkEntity, Long>, JpaSpecificationExecutor<WorkEntity> {
    Optional<WorkEntity> findByWorkTitleIgnoreCase(String workTitle);
    Page<WorkEntity> findByWorkTypeId(Long workTypeId, Pageable pageable);
    List<WorkEntity> findByWorkType(Long workTypeId);
    List<WorkEntity> findByWorkTypeIdAndTeacherId(Long workTypeId, Long userId);
}
