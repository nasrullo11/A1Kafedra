package uz.istart.kafedra.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.istart.kafedra.core.entities.WeekdayEntity;

import java.util.List;
import java.util.Optional;

public interface WeekdayRepository extends JpaRepository<WeekdayEntity,Long>, JpaSpecificationExecutor<WeekdayEntity> {
    Optional<List<WeekdayEntity>> findByWeekdayNameIgnoreCase(String weekdayName);
}
