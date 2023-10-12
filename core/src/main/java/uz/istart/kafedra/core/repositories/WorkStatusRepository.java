package uz.istart.kafedra.core.repositories;

import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.WorkStatusEntity;

import java.util.List;
import java.util.Optional;

public interface WorkStatusRepository extends JpaRepository<WorkStatusEntity,Long>, JpaSpecificationExecutor<WorkStatusEntity> {
    Optional<List<WorkStatusEntity>> findByWorkStatusNameIgnoreCase(String workStatusName);
}
