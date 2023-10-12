package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.FieldEntity;

import java.util.List;
import java.util.Optional;

public interface FieldRepository extends JpaRepository<FieldEntity,Long>, JpaSpecificationExecutor<FieldEntity> {
    Optional<List<FieldEntity>> findByFieldNameIgnoreCase(String fieldName);
}
