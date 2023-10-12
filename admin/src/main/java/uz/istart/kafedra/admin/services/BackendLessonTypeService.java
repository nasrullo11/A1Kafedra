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

import uz.istart.kafedra.core.dtos.LessonTypeDto;
import uz.istart.kafedra.core.entities.LessonTypeEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.LessonTypeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendLessonTypeService {
    private final LessonTypeRepository lessonTypeRepository;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public LessonTypeDto findById(Long id) {
        Optional<LessonTypeEntity> lessonTypeEntity = lessonTypeRepository.findById(id);
        if (lessonTypeEntity.isPresent()) {
            LessonTypeDto lessonTypeDto = lessonTypeEntity.get().getDto();
            return lessonTypeDto;
        }
        throw new TableEntityNotFoundException("LessonType not found by ID[" + id + "] exception");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<LessonTypeDto> getLessonTypeDataTables(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<LessonTypeEntity> lessonTypeEntityPage = lessonTypeRepository.findAll(pageRequest);

        List<LessonTypeDto> lessonTypeDtos = lessonTypeEntityPage.get()
                .map(LessonTypeEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<LessonTypeDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) lessonTypeEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) lessonTypeEntityPage.getTotalElements());
        dataTablesForm.setData(lessonTypeDtos);

        return dataTablesForm;
    }

//    @Transactional
//    public void saveBackendGroup(GroupDto groupDto) {
//        GroupEntity groupEntity = new GroupEntity();
//        BeanUtils.copyProperties(groupDto, groupEntity);
//        if (Objects.nonNull(groupDto.getGroupEducationType()) && Objects.nonNull(groupDto.getGroupEducationType().getId()) )
//            groupEntity.setEducationTypeId(groupDto.getGroupEducationType().getId());
//        if (Objects.nonNull(groupDto.getGroupField()) && Objects.nonNull(groupDto.getGroupField().getId()) )
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
    public List<LessonTypeDto> getList() {
        return lessonTypeRepository.findAll()
                .stream()
                .map(LessonTypeEntity::getDto)
                .collect(Collectors.toList());
    }
}
