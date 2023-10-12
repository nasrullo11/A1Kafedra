package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.istart.kafedra.core.dtos.GroupByFieldsDto;
import uz.istart.kafedra.core.entities.GroupEntity;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity,Long>, JpaSpecificationExecutor<GroupEntity> {
    Optional<GroupEntity> findByGroupNameIgnoreCase(String groupName);

    @Query(value = "SELECT f.field_name AS fieldName, kg.entrance_year AS enterYear, COUNT(kg.field_id) AS amountGroups FROM kafedra_groups AS kg LEFT JOIN kafedra_fields f ON f.id = kg.field_id GROUP BY field_id, field_name, entrance_year", nativeQuery = true)
    List<GroupByFieldsDto> getNumberOfGroups();
}
