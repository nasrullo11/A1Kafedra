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
import uz.istart.kafedra.admin.form.WorkStatusForm;
import uz.istart.kafedra.admin.form.tables.DataTablesForm;
import uz.istart.kafedra.admin.form.tables.FilterForm;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.dtos.WorkDto;
import uz.istart.kafedra.core.dtos.WorkStatusDto;
import uz.istart.kafedra.core.dtos.WorkTypeDto;
import uz.istart.kafedra.core.entities.WorkEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.WorkRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendWorkService {

    private final WorkRepository workRepository;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public WorkDto findById(Long id) {
        Optional<WorkEntity> workEntity = workRepository.findById(id);
        if (workEntity.isPresent()) {
            WorkDto workDto = workEntity.get().getDto();
            UserDto teacherDto = new UserDto();
            if (Objects.nonNull(workEntity.get().getWorkTeacher())) {
                teacherDto.setId(workEntity.get().getWorkTeacher().getId());
                teacherDto.setUsername(workEntity.get().getWorkTeacher().getUsername());
                teacherDto.setFullName(workEntity.get().getWorkTeacher().getFullName());
            }
            workDto.setWorkTeacher(teacherDto);

            WorkTypeDto wtDto = new WorkTypeDto();
            if (Objects.nonNull(workEntity.get().getWorkType())) {
                wtDto.setId(workEntity.get().getWorkType().getId());
                wtDto.setWorkTypeName(workEntity.get().getWorkType().getWorkTypeName());
            }
            workDto.setWorkType(wtDto);

            WorkStatusDto wsDto = new WorkStatusDto();
            if (Objects.nonNull(workEntity.get().getWorkStatus())) {
                wsDto.setId(workEntity.get().getWorkStatus().getId());
                wsDto.setWorkStatusName(workEntity.get().getWorkStatus().getWorkStatusName());
            }
            workDto.setWorkStatus(wsDto);

            return workDto;
        }
        throw new TableEntityNotFoundException("Work not found by ID[" + id + "] exception");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<WorkDto> getWorkDataTablesByTypeId1(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<WorkEntity> workEntityPage = workRepository.findByWorkTypeId(1l, pageRequest);

        List<WorkDto> workDtos = workEntityPage.get()
                .map(WorkEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<WorkDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) workEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) workEntityPage.getTotalElements());
        dataTablesForm.setData(workDtos);

        return dataTablesForm;
    }

    @Transactional(readOnly = true)
    public DataTablesForm<WorkDto> getWorkDataTablesByTypeId2(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<WorkEntity> workEntityPage = workRepository.findByWorkTypeId(2l, pageRequest);

        List<WorkDto> workDtos = workEntityPage.get()
                .map(WorkEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<WorkDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) workEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) workEntityPage.getTotalElements());
        dataTablesForm.setData(workDtos);

        return dataTablesForm;
    }

    @Transactional(readOnly = true)
    public DataTablesForm<WorkDto> getWorkDataTablesByTypeId3(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<WorkEntity> workEntityPage = workRepository.findByWorkTypeId(3l, pageRequest);

        List<WorkDto> workDtos = workEntityPage.get()
                .map(WorkEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<WorkDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) workEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) workEntityPage.getTotalElements());
        dataTablesForm.setData(workDtos);

        return dataTablesForm;
    }

    @Transactional(readOnly = true)
    public DataTablesForm<WorkDto> getWorkDataTablesByTypeId4(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<WorkEntity> workEntityPage = workRepository.findByWorkTypeId(4l, pageRequest);

        List<WorkDto> workDtos = workEntityPage.get()
                .map(WorkEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<WorkDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) workEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) workEntityPage.getTotalElements());
        dataTablesForm.setData(workDtos);

        return dataTablesForm;
    }

    @Transactional
    public void saveBackendWork(WorkDto workDto) {
        WorkEntity workEntity = new WorkEntity();
        BeanUtils.copyProperties(workDto, workEntity);
        if (Objects.nonNull(workDto.getWorkTeacher()) && Objects.nonNull(workDto.getWorkTeacher().getId()))
            workEntity.setTeacherId(workDto.getWorkTeacher().getId());
        if (Objects.nonNull(workDto.getWorkType()) && Objects.nonNull(workDto.getWorkType().getId()))
            workEntity.setWorkTypeId(workDto.getWorkType().getId());
        if (Objects.nonNull(workDto.getWorkStatus()) && Objects.nonNull(workDto.getWorkStatus().getId()))
            workEntity.setWorkStatusId(workDto.getWorkStatus().getId());
        workRepository.save(workEntity);
    }

    @Transactional
    public String editBackendWork(WorkDto workDto, BindingResult bindingResult, boolean isProfile) {
        WorkEntity workEntity = new WorkEntity();
        if (workDto.getId() != null) {
            Optional<WorkEntity> workEntityOptional = workRepository.findById(workDto.getId());
            if (workEntityOptional.isPresent()) {
                workEntity = workEntityOptional.get();
                if (isProfile)
                    return "works/profile";
            } else {
                throw new TableEntityNotFoundException("Work not found by  id[" + workDto.getId() + "] exception!");
            }
        } else {
            throw new TableEntityNotFoundException("Work not found exception!");
        }
        BeanUtils.copyProperties(workDto, workEntity);
        if (Objects.nonNull(workDto.getWorkTeacher()) && Objects.nonNull(workDto.getWorkTeacher().getId()))
            workEntity.setTeacherId(workDto.getWorkTeacher().getId());
        if (Objects.nonNull(workDto.getWorkType()) && Objects.nonNull(workDto.getWorkType().getId()))
            workEntity.setWorkTypeId(workDto.getWorkType().getId());
        if (Objects.nonNull(workDto.getWorkStatus()) && Objects.nonNull(workDto.getWorkStatus().getId()))
            workEntity.setWorkStatusId(workDto.getWorkStatus().getId());
        workRepository.save(workEntity);
        if(isProfile){
            return "redirect:/dashboard/main.do";
        }
        else if(workDto.getWorkType().getId() == 1L){
            return "redirect:/works/listType1.do";
        }
        else if(workDto.getWorkType().getId() == 2L){
            return "redirect:/works/listType2.do";
        }
        else if(workDto.getWorkType().getId() == 3L){
            return "redirect:/works/listType3.do";
        }
        else{
            return "redirect:/works/listType4.do";
        }
    }

    @Transactional(readOnly = true)
    public List<WorkDto> getList() {
        return workRepository.findAll()
                .stream()
                .map(WorkEntity::getDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WorkDto> getListByType(Long typeId) {
        return workRepository.findByWorkType(typeId)
                .stream()
                .map(WorkEntity::getDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeWorkStatus(WorkStatusForm statusForm) {
        System.out.println(statusForm.getStatusId() + " ----- " + statusForm.getEntityId() + " ----- " + statusForm.isEnable());
        if (statusForm.getEntityId() != null) {
            Optional<WorkEntity> workEntity = workRepository.findById(statusForm.getEntityId());
            if (workEntity.isPresent()) {
                workEntity.get().setWorkStatusId(statusForm.getStatusId());
                workRepository.save(workEntity.get());
            } else {
                throw new TableEntityNotFoundException(statusForm.getEntityId() + " ID ga ega pedagogik ish topilmadi!");
            }
        } else {
            throw new TableEntityNotFoundException("Pedagogik ish topilmadi!");
        }
    }

}
