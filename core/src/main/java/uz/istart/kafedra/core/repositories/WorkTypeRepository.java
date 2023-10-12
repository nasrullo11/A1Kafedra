package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.WorkTypeEntity;

import java.util.List;
import java.util.Optional;

public interface WorkTypeRepository extends JpaRepository<WorkTypeEntity,Long>, JpaSpecificationExecutor<WorkTypeEntity> {
    Optional<List<WorkTypeEntity>> findByWorkTypeNameIgnoreCase(String workTypeName);
}
