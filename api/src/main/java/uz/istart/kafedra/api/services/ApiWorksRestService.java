package uz.istart.kafedra.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.istart.kafedra.core.entities.WorkEntity;
import uz.istart.kafedra.core.repositories.WorkRepository;

import java.util.List;

@Service
public class ApiWorksRestService {

    @Autowired
    WorkRepository workRepository;

    @Transactional(readOnly = true)
    public List<WorkEntity> getWorksList(Long workTypeId, Long userId) {
        return workRepository.findByWorkTypeIdAndTeacherId(workTypeId, userId);
    }

    @Transactional
    public void saveWork(Long userId, String workTitle, Long workTypeId, String workDeadline, String workDescription) {
        WorkEntity workEntity = new WorkEntity();
        workEntity.setTeacherId(userId);
        workEntity.setWorkDeadline(workDeadline);
        workEntity.setWorkStatusId(1l);
        workEntity.setWorkTitle(workTitle);
        workEntity.setWorkTypeId(workTypeId);
        workEntity.setWorkDescription(workDescription);
        workRepository.save(workEntity);
    }

    @Transactional
    public void editWork(WorkEntity workEntity, String workTitle, String workDeadline, String workDescription) {
        workEntity.setWorkDeadline(workDeadline);
        workEntity.setWorkTitle(workTitle);
        workEntity.setWorkDescription(workDescription);
        workRepository.save(workEntity);
    }

    @Transactional
    public void deleteWork(WorkEntity workEntity) {
        workRepository.delete(workEntity);
    }

    @Transactional
    public void changeStatus(WorkEntity workEntity, Long statusId){
        workEntity.setWorkStatusId(statusId);
        workRepository.save(workEntity);
    }
}
