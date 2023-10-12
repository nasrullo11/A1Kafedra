package uz.istart.kafedra.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uz.istart.kafedra.api.services.ApiDistributionsRestService;
import uz.istart.kafedra.core.dtos.DistributionDto;
import uz.istart.kafedra.core.dtos.GroupDto;
import uz.istart.kafedra.core.dtos.SubjectDto;
import uz.istart.kafedra.core.entities.DistributionEntity;
import uz.istart.kafedra.core.entities.GroupEntity;
import uz.istart.kafedra.core.entities.SubjectEntity;
import uz.istart.kafedra.core.entities.UserEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.DistributionRepository;
import uz.istart.kafedra.core.repositories.SubjectRepository;
import uz.istart.kafedra.core.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/distributions")
@RequiredArgsConstructor
public class ApiDistributionsController {

    private final ApiDistributionsRestService apiDistributionsRestService;
    private final DistributionRepository distributionRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @GetMapping(value = "/list-by-userId")
    @ResponseBody
    public List<DistributionDto> getDistributions(Long userId) {
        return distributionRepository.findByUserId(userId)
                .stream()
                .map(DistributionEntity::getDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/list-user-subjects")
    @ResponseBody
    public List<SubjectDto> getUserSubjects(Long userId){
        System.out.println(userId);
        List<DistributionEntity> disList = distributionRepository.findByUserId(userId);
        List<SubjectDto> sub = apiDistributionsRestService.getSubjects(disList)
                .stream().map(SubjectEntity::getDto)
                .collect(Collectors.toList());
        return sub.stream().distinct().collect(Collectors.toList());
    }

    @GetMapping(value = "/list-user-groups")
    @ResponseBody
    public List<GroupDto> getUserGroups(Long userId){
        System.out.println(userId);
        List<DistributionEntity> disList = distributionRepository.findByUserId(userId);
        List<GroupDto> sub = apiDistributionsRestService.getGroups(disList)
                .stream().map(GroupEntity::getDto)
                .collect(Collectors.toList());
        return sub.stream().distinct().collect(Collectors.toList());
    }


    @GetMapping(value = "/list-department-subjects")
    @ResponseBody
    public List<SubjectDto> getDepartmentSubjects(Long userId){
        UserEntity userEntity = new UserEntity();
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            userEntity = userEntityOptional.get();
            return subjectRepository.findByDepartmentId(userEntity.getDepartmentId()).stream().map(SubjectEntity::getDto).collect(Collectors.toList());
        } else {
            throw new TableEntityNotFoundException("Serverda xatolik yuz berdi!");
        }
    }
}
