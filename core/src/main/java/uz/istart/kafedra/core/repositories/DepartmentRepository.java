package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.DepartmentEntity;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long>, JpaSpecificationExecutor<DepartmentEntity>{
    Optional<List<DepartmentEntity>> findByDepartmentNameIgnoreCase(String departmentName);
}
