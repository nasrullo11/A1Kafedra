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
import uz.istart.kafedra.core.dtos.DepartmentDto;
import uz.istart.kafedra.core.dtos.SubjectDto;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.entities.SubjectEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.SubjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendSubjectService {
    private final SubjectRepository subjectRepository;
    private final BackendUserService backendUserService;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public SubjectDto findById(Long id) {
        Optional<SubjectEntity> subjectEntity = subjectRepository.findById(id);
        if (subjectEntity.isPresent()) {
            SubjectDto subjectDto = subjectEntity.get().getDto();
            DepartmentDto departmentDto = new DepartmentDto();
            if (Objects.nonNull(subjectEntity.get().getSubjectDepartment())) {
                departmentDto.setId(subjectEntity.get().getSubjectDepartment().getId());
                departmentDto.setDepartmentName(subjectEntity.get().getSubjectDepartment().getDepartmentName());
            }
            subjectDto.setSubjectDepartment(departmentDto);
            return subjectDto;
        }
        throw new TableEntityNotFoundException("Subject not found by ID[" + id + "] exception");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<SubjectDto> getSubjectDataTables(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<SubjectEntity> subjectEntityPage = subjectRepository.findAll(pageRequest);

        List<SubjectDto> subjectDtos = subjectEntityPage.get()
                .map(SubjectEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<SubjectDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) subjectEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) subjectEntityPage.getTotalElements());
        dataTablesForm.setData(subjectDtos);

        return dataTablesForm;
    }

    @Transactional
    public void saveBackendSubject(SubjectDto subjectDto) {
        SubjectEntity subjectEntity = new SubjectEntity();
        BeanUtils.copyProperties(subjectDto, subjectEntity, "totalHour");
        if (Objects.nonNull(subjectDto.getSubjectDepartment()) && Objects.nonNull(subjectDto.getSubjectDepartment().getId()) )
            subjectEntity.setDepartmentId(subjectDto.getSubjectDepartment().getId());
        subjectEntity.setTotalHour(0);
        subjectRepository.save(subjectEntity);
    }

    @Transactional
    public String editBackendSubject(SubjectDto subjectDto, BindingResult bindingResult, boolean isProfile) {
        SubjectEntity subjectEntity = new SubjectEntity();
        if (subjectDto.getId() != null) {
            Optional<SubjectEntity> subjectEntityOptional = subjectRepository.findById(subjectDto.getId());
            if (subjectEntityOptional.isPresent()) {
                subjectEntity = subjectEntityOptional.get();
                if (isProfile)
                    return "subjects/profile";
            } else {
                throw new TableEntityNotFoundException("Subject not found by  id[" + subjectDto.getId() + "] exception!");
            }
        } else {
            throw new TableEntityNotFoundException("Subject not found exception!");
        }
        BeanUtils.copyProperties(subjectDto, subjectEntity, "totalHour");
        if (Objects.nonNull(subjectDto.getSubjectDepartment()) && Objects.nonNull(subjectDto.getSubjectDepartment().getId()) )
            subjectEntity.setDepartmentId(subjectDto.getSubjectDepartment().getId());
        subjectRepository.save(subjectEntity);

        return isProfile ? "redirect:/dashboard/main.do" : "redirect:/subjects/list.do";
    }

    @Transactional(readOnly = true)
    public List<SubjectDto> getList() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectEntity::getDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SubjectDto> getListByDepartment(Long departmentId) {
        System.out.println(departmentId + " kafedra id");
        return subjectRepository.findByDepartmentId(departmentId)
                .stream()
                .map(SubjectEntity::getDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeSubjectDepartment(Long entityId) {
        UserDto user = backendUserService.findByAuth();
        if (entityId != null) {
            Optional<SubjectEntity> subjectEntityOptional = subjectRepository.findById(entityId);
            if (subjectEntityOptional.isPresent()) {
                SubjectEntity subjectEntity = new SubjectEntity();
                subjectEntity = subjectEntityOptional.get();
                subjectEntity.setDepartmentId(user.getDepartment().getId());
                subjectRepository.save(subjectEntity);
            } else {
                throw new TableEntityNotFoundException(entityId + " ID ga ega pedagogik ish topilmadi!");
            }
        } else {
            throw new TableEntityNotFoundException("Pedagogik ish topilmadi!");
        }
    }

}

