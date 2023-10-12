package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.MonthEntity;

import java.util.List;
import java.util.Optional;

public interface MonthRepository extends JpaRepository<MonthEntity,Long>, JpaSpecificationExecutor<MonthEntity> {
    Optional<List<MonthEntity>> findByMonthNameIgnoreCase(String monthName);
}
