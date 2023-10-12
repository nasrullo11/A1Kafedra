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

import uz.istart.kafedra.core.dtos.WorkTypeDto;
import uz.istart.kafedra.core.entities.WorkTypeEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.WorkTypeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendWorkTypeService {
    private final WorkTypeRepository workTypeRepository;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public WorkTypeDto findById(Long id) {
        Optional<WorkTypeEntity> workTypeEntity = workTypeRepository.findById(id);
        System.out.println();
        if (workTypeEntity.isPresent()) {
            WorkTypeDto workTypeDto = workTypeEntity.get().getDto();
            return workTypeDto;
        }
        throw new TableEntityNotFoundException("WorkType not found by ID[" + id + "] exception");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<WorkTypeDto> getWorkTypeDataTables(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<WorkTypeEntity> workTypeEntityPage = workTypeRepository.findAll(pageRequest);

        List<WorkTypeDto> workTypeDtos = workTypeEntityPage.get()
                .map(WorkTypeEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<WorkTypeDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) workTypeEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) workTypeEntityPage.getTotalElements());
        dataTablesForm.setData(workTypeDtos);

        return dataTablesForm;
    }

//    @Transactional
//    public void saveBackendGroup(GroupDto groupDto) {
//        GroupEntity groupEntity = new GroupEntity();
//        BeanUtils.copyProperties(groupDto, groupEntity);
//        if (Objects.nonNull(groupDto.getGroupEducationType()) && Objects.nonNull(groupDto.getGroupEducationType().getId()) )
//            groupEntity.setEducationTypeId(groupDto.getGroupEducationType().getId());
//        if (Objects.nonNull(groupDto.getGroupworkType()) && Objects.nonNull(groupDto.getGroupField().getId()) )
//            groupEntity.setFieldId(groupDto.getGroupField().getId());
//        groupRepository.save(groupEntity);
//    }

//    @Transactional
//    public String editBackendGroup(GroupDto groupDto, BindingResult bindingResult, boolean isProfile) {
//        GroupEntity groupEntity = new GroupEntity();
//        if (groupDto.getId() != null) {
//            Optional<GroupEntity> groupEntityOptional = groupRepository.findById(groupDto.getId());
//            if (groupEntityOptional.isPresent()) {
//                groupEntity = groupEntityOptional.get();
//                if (isProfile)
//                    return "groups/profile";
//            } else {
//                throw new TableEntityNotFoundException("Group not found by  id[" + groupDto.getId() + "] exception!");
//            }
//        } else {
//            throw new TableEntityNotFoundException("Group not found exception!");
//        }
//        BeanUtils.copyProperties(groupDto, groupEntity);
//        if (Objects.nonNull(groupDto.getGroupEducationType()) && Objects.nonNull(groupDto.getGroupEducationType().getId()) )
//            groupEntity.setEducationTypeId(groupDto.getGroupEducationType().getId());
//        if (Objects.nonNull(groupDto.getGroupField()) && Objects.nonNull(groupDto.getGroupField().getId()) )
//            groupEntity.setFieldId(groupDto.getGroupField().getId());
//        groupRepository.save(groupEntity);
//
//        return isProfile ? "redirect:/dashboard/main.do" : "redirect:/groups/list.do";
//    }

    @Transactional(readOnly = true)
    public List<WorkTypeDto> getList() {
        return workTypeRepository.findAll()
                .stream()
                .map(WorkTypeEntity::getDto)
                .collect(Collectors.toList());
    }
}
