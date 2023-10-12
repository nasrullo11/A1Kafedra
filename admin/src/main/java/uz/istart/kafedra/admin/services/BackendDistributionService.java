package uz.istart.kafedra.admin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import uz.istart.kafedra.admin.form.tables.DataTablesForm;
import uz.istart.kafedra.admin.form.tables.FilterForm;
import uz.istart.kafedra.core.dtos.*;
import uz.istart.kafedra.core.entities.DistributionEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.DistributionRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendDistributionService {
    private final DistributionRepository distributionRepository;
    private final BackendUserService backendUserService;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public DistributionByUsersDto getDistributionByUser(Long id){
        List<DistributionByUsersDto> getDistributionList = distributionRepository.getLoadByUserId();
        for(DistributionByUsersDto i : getDistributionList){
            if(i.getUserId().equals(id)){
                return i;
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public DistributionDto findById(Long id) {
        Optional<DistributionEntity> distributionEntity = distributionRepository.findById(id);
        if (distributionEntity.isPresent()) {

            DistributionDto distributionDto = distributionEntity.get().getDto();
            UserDto userDto = new UserDto();
            if (Objects.nonNull(distributionEntity.get().getDistributionUser())) {
                userDto.setId(distributionEntity.get().getDistributionUser().getId());
                userDto.setUsername(distributionEntity.get().getDistributionUser().getUsername());
                userDto.setFullName(distributionEntity.get().getDistributionUser().getFullName());
            }
            distributionDto.setDistributionUser(userDto);

            SubjectDto subDto = new SubjectDto();
            if (Objects.nonNull(distributionEntity.get().getDistributionSubject())) {
                subDto.setId(distributionEntity.get().getDistributionSubject().getId());
                subDto.setSubjectName(distributionEntity.get().getDistributionSubject().getSubjectName());
            }
            distributionDto.setDistributionSubject(subDto);

            GroupDto grDto = new GroupDto();
            if (Objects.nonNull(distributionEntity.get().getDistributionGroup())) {
                grDto.setId(distributionEntity.get().getDistributionGroup().getId());
                grDto.setGroupName(distributionEntity.get().getDistributionGroup().getGroupName());
                grDto.setEntranceYear(distributionEntity.get().getDistributionGroup().getEntranceYear());
                grDto.setAmountStudents(distributionEntity.get().getDistributionGroup().getAmountStudents());
            }
            distributionDto.setDistributionGroup(grDto);

            LessonTypeDto lTypeDto = new LessonTypeDto();
            if (Objects.nonNull(distributionEntity.get().getDistributionLessonType())) {
                lTypeDto.setId(distributionEntity.get().getDistributionLessonType().getId());
                lTypeDto.setLessonTypeName(distributionEntity.get().getDistributionLessonType().getLessonTypeName());
            }
            distributionDto.setDistributionLessonType(lTypeDto);

            DepartmentDto depDto = new DepartmentDto();
            if (Objects.nonNull(distributionEntity.get().getDistributionDepartment())) {
                depDto.setId(distributionEntity.get().getDistributionDepartment().getId());
                depDto.setDepartmentName(distributionEntity.get().getDistributionDepartment().getDepartmentName());
            }
            distributionDto.setDistributionDepartment(depDto);

            return distributionDto;
        }
        throw new TableEntityNotFoundException("Distribution not found by ID[" + id + "] exception");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<DistributionDto> getDistributionsByDepartment(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        UserDto user = backendUserService.findByAuth();
        Page<DistributionEntity> distributionEntityPage = distributionRepository.findByDepartmentId(user.getDepartment().getId(), pageRequest);

        List<DistributionDto> distributionDtos = distributionEntityPage.get()
                .map(DistributionEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<DistributionDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) distributionEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) distributionEntityPage.getTotalElements());
        dataTablesForm.setData(distributionDtos);

        return dataTablesForm;
    }

    @Transactional(readOnly = true)
    public DataTablesForm<DistributionDto> getDistributionDataTables(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<DistributionEntity> distributionEntityPage = distributionRepository.findAll(pageRequest);

        List<DistributionDto> distributionDtos = distributionEntityPage.get()
                .map(DistributionEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<DistributionDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) distributionEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) distributionEntityPage.getTotalElements());
        dataTablesForm.setData(distributionDtos);

        return dataTablesForm;
    }

    @Transactional
    public void saveBackendDistribution(DistributionDto distributionDto) {
        DistributionEntity distributionEntity = new DistributionEntity();
        BeanUtils.copyProperties(distributionDto, distributionEntity, "distributionDepartment");
        UserDto userDto = backendUserService.findByAuth();
        distributionEntity.setDepartmentId(userDto.getDepartment().getId());
        if (Objects.nonNull(distributionDto.getDistributionUser()) && Objects.nonNull(distributionDto.getDistributionUser().getId()) )
            distributionEntity.setUserId(distributionDto.getDistributionUser().getId());
        if (Objects.nonNull(distributionDto.getDistributionSubject()) && Objects.nonNull(distributionDto.getDistributionSubject().getId()) )
            distributionEntity.setSubjectId(distributionDto.getDistributionSubject().getId());
        if (Objects.nonNull(distributionDto.getDistributionGroup()) && Objects.nonNull(distributionDto.getDistributionGroup().getId()) )
            distributionEntity.setGroupId(distributionDto.getDistributionGroup().getId());
        if (Objects.nonNull(distributionDto.getDistributionLessonType()) && Objects.nonNull(distributionDto.getDistributionLessonType().getId()) )
            distributionEntity.setLessonTypeId(distributionDto.getDistributionLessonType().getId());
        distributionEntity.setSubjectHour(distributionDto.getSubjectHour()+distributionDto.getControlHour());
        distributionRepository.save(distributionEntity);
    }

    @Transactional
    public String editBackendDistribution(DistributionDto distributionDto, BindingResult bindingResult, boolean isProfile) {
        DistributionEntity distributionEntity = new DistributionEntity();
        if (distributionDto.getId() != null) {
            Optional<DistributionEntity> distributionEntityOptional = distributionRepository.findById(distributionDto.getId());
            if (distributionEntityOptional.isPresent()) {
                distributionEntity = distributionEntityOptional.get();
                if (isProfile)
                    return "distributions/profile";
            } else {
                throw new TableEntityNotFoundException("Distribution not found by  id[" + distributionDto.getId() + "] exception!");
            }
        } else {
            throw new TableEntityNotFoundException("Distribution not found exception!");
        }

        BeanUtils.copyProperties(distributionDto, distributionEntity, "distributionDepartment");
        UserDto userDto = backendUserService.findByAuth();
        distributionEntity.setDepartmentId(userDto.getDepartment().getId());
        if (Objects.nonNull(distributionDto.getDistributionUser()) && Objects.nonNull(distributionDto.getDistributionUser().getId()) )
            distributionEntity.setUserId(distributionDto.getDistributionUser().getId());
        if (Objects.nonNull(distributionDto.getDistributionSubject()) && Objects.nonNull(distributionDto.getDistributionSubject().getId()) )
            distributionEntity.setSubjectId(distributionDto.getDistributionSubject().getId());
        if (Objects.nonNull(distributionDto.getDistributionGroup()) && Objects.nonNull(distributionDto.getDistributionGroup().getId()) )
            distributionEntity.setGroupId(distributionDto.getDistributionGroup().getId());
        if (Objects.nonNull(distributionDto.getDistributionLessonType()) && Objects.nonNull(distributionDto.getDistributionLessonType().getId()) )
            distributionEntity.setLessonTypeId(distributionDto.getDistributionLessonType().getId());
        distributionRepository.save(distributionEntity);

        return isProfile ? "redirect:/dashboard/main.do" : "redirect:/distributions/list.do";
    }

    @Transactional(readOnly = true)
    public List<DistributionDto> getList() {
        return distributionRepository.findAll()
                .stream()
                .map(DistributionEntity::getDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DistributionDto> getListByDep() {
        UserDto user = backendUserService.findByAuth();
        return distributionRepository.findByDepartmentId(user.getDepartment().getId())
                .stream()
                .map(DistributionEntity::getDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDistribution(Long id) {
        if (id != null) {
            Optional<DistributionEntity> disEntity = distributionRepository.findById(id);
            if (disEntity.isPresent()) {
                distributionRepository.delete(disEntity.get());
            } else {
                throw new TableEntityNotFoundException(id + " ID ga ega taqsimot topilmadi!");
            }
        } else {
            throw new TableEntityNotFoundException("Taqsimot topilmadi!");
        }
    }
}
