package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.EducationTypeEntity;

import java.util.List;
import java.util.Optional;

public interface EducationTypeRepository extends JpaRepository<EducationTypeEntity,Long>, JpaSpecificationExecutor<EducationTypeEntity> {
    Optional<List<EducationTypeEntity>> findByEducationTypeNameIgnoreCase(String educationTypeName);
}
