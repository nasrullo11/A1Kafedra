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
import uz.istart.kafedra.core.dtos.EducationTypeDto;
import uz.istart.kafedra.core.dtos.FieldDto;
import uz.istart.kafedra.core.dtos.GroupByFieldsDto;
import uz.istart.kafedra.core.dtos.GroupDto;
import uz.istart.kafedra.core.entities.GroupEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.GroupRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendGroupService {
    private final GroupRepository groupRepository;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public GroupDto findById(Long id) {
        Optional<GroupEntity> groupEntity = groupRepository.findById(id);
        if (groupEntity.isPresent()) {
            GroupDto groupDto = groupEntity.get().getDto();
            FieldDto fieldDto = new FieldDto();
            if (Objects.nonNull(groupEntity.get().getGroupField())) {
                fieldDto.setId(groupEntity.get().getGroupField().getId());
                fieldDto.setFieldName(groupEntity.get().getGroupField().getFieldName());
                fieldDto.setFieldCode(groupEntity.get().getGroupField().getFieldCode());
            }
            groupDto.setGroupField(fieldDto);

            EducationTypeDto eduDto = new EducationTypeDto();
            if (Objects.nonNull(groupEntity.get().getGroupEducationType())) {
                eduDto.setId(groupEntity.get().getGroupEducationType().getId());
                eduDto.setEducationTypeName(groupEntity.get().getGroupEducationType().getEducationTypeName());
            }
            groupDto.setGroupEducationType(eduDto);

            return groupDto;
        }
        throw new TableEntityNotFoundException("Group not found by ID[" + id + "] exception");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<GroupDto> getGroupDataTables(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "entranceYear");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<GroupEntity> groupEntityPage = groupRepository.findAll(pageRequest);

        List<GroupDto> groupDtos = groupEntityPage.get()
                .map(GroupEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<GroupDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) groupEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) groupEntityPage.getTotalElements());
        dataTablesForm.setData(groupDtos);

        return dataTablesForm;
    }

    @Transactional(readOnly = true)
    public List<GroupByFieldsDto> getGroupNumbersByFields() {
        return groupRepository.getNumberOfGroups();
    }

    @Transactional
    public void saveBackendGroup(GroupDto groupDto) {
        GroupEntity groupEntity = new GroupEntity();
        BeanUtils.copyProperties(groupDto, groupEntity);
        if (Objects.nonNull(groupDto.getGroupEducationType()) && Objects.nonNull(groupDto.getGroupEducationType().getId()) )
            groupEntity.setEducationTypeId(groupDto.getGroupEducationType().getId());
        if (Objects.nonNull(groupDto.getGroupField()) && Objects.nonNull(groupDto.getGroupField().getId()) )
            groupEntity.setFieldId(groupDto.getGroupField().getId());
        groupRepository.save(groupEntity);
    }

    @Transactional
    public String editBackendGroup(GroupDto groupDto, BindingResult bindingResult, boolean isProfile) {
        GroupEntity groupEntity = new GroupEntity();
        if (groupDto.getId() != null) {
            Optional<GroupEntity> groupEntityOptional = groupRepository.findById(groupDto.getId());
            if (groupEntityOptional.isPresent()) {
                groupEntity = groupEntityOptional.get();
                if (isProfile)
                        return "groups/profile";
            } else {
                throw new TableEntityNotFoundException("Group not found by  id[" + groupDto.getId() + "] exception!");
            }
        } else {
            throw new TableEntityNotFoundException("Group not found exception!");
        }
        BeanUtils.copyProperties(groupDto, groupEntity);
        if (Objects.nonNull(groupDto.getGroupEducationType()) && Objects.nonNull(groupDto.getGroupEducationType().getId()) )
            groupEntity.setEducationTypeId(groupDto.getGroupEducationType().getId());
        if (Objects.nonNull(groupDto.getGroupField()) && Objects.nonNull(groupDto.getGroupField().getId()) )
            groupEntity.setFieldId(groupDto.getGroupField().getId());
        groupRepository.save(groupEntity);

        return isProfile ? "redirect:/dashboard/main.do" : "redirect:/groups/list.do";
    }

    @Transactional(readOnly = true)
    public List<GroupDto> getList() {
        return groupRepository.findAll()
                .stream()
                .map(GroupEntity::getDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteGroup(Long id) {
        if (id != null) {
            Optional<GroupEntity> disEntity = groupRepository.findById(id);
            if (disEntity.isPresent()) {
                groupRepository.delete(disEntity.get());
            } else {
                throw new TableEntityNotFoundException(id + " ID ga ega guruh topilmadi!");
            }
        } else {
            throw new TableEntityNotFoundException("Guruh topilmadi!");
        }
    }

}
