package uz.istart.kafedra.core.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.istart.kafedra.core.dtos.DistributionByUsersDto;
import uz.istart.kafedra.core.entities.DistributionEntity;

import java.util.List;

public interface DistributionRepository extends JpaRepository<DistributionEntity,Long>, JpaSpecificationExecutor<DistributionEntity> {
    List<DistributionEntity> findByUserId(Long userId);
    List<DistributionEntity> findByDepartmentId(Long departmentId);
    Page<DistributionEntity> findByDepartmentId(Long departmentId, Pageable pageable);

    @Query(value = "SELECT l.user_id AS userId, ku.username AS username, ku.full_name AS fullName, SUM(l.distribution_hour) AS totalHour FROM kafedra_distributions AS l LEFT JOIN kafedra_users ku ON l.user_id = ku.id GROUP BY user_id, username, full_name", nativeQuery = true)
    List<DistributionByUsersDto> getLoadByUserId();
}
