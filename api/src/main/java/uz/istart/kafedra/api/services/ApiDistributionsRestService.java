package uz.istart.kafedra.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.istart.kafedra.core.entities.DistributionEntity;
import uz.istart.kafedra.core.entities.GroupEntity;
import uz.istart.kafedra.core.entities.SubjectEntity;
import uz.istart.kafedra.core.repositories.DistributionRepository;
import uz.istart.kafedra.core.repositories.GroupRepository;
import uz.istart.kafedra.core.repositories.SubjectRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApiDistributionsRestService {

    @Autowired
    DistributionRepository distributionRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public List<SubjectEntity> getSubjects(List<DistributionEntity> disList){
        Set<SubjectEntity> subList = new HashSet<>();
        for(DistributionEntity dis : disList){
            Optional<SubjectEntity> subjectEntity = subjectRepository.findById(dis.getSubjectId());
            SubjectEntity subject = subjectEntity.get();
            subList.add(subject);
        }
        return subList.stream().collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupEntity> getGroups(List<DistributionEntity> disList){
        Set<GroupEntity> grList = new HashSet<>();
        for(DistributionEntity dis : disList){
            Optional<GroupEntity> groupEntity = groupRepository.findById(dis.getGroupId());
            GroupEntity group = groupEntity.get();
            grList.add(group);
        }
        return grList.stream().collect(Collectors.toList());
    }

}
