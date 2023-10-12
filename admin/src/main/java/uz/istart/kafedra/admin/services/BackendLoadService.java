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
import uz.istart.kafedra.core.dtos.FieldDto;
import uz.istart.kafedra.core.dtos.LoadBySubjectsDto;
import uz.istart.kafedra.core.dtos.LoadDto;
import uz.istart.kafedra.core.dtos.SubjectDto;
import uz.istart.kafedra.core.entities.LoadEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.LoadBySubjectsRepository;
import uz.istart.kafedra.core.repositories.LoadRepository;
import uz.istart.kafedra.core.repositories.SubjectRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendLoadService {

    private final LoadRepository loadRepository;
    private final LoadBySubjectsRepository loadBySubjectsRepository;
    private final SubjectRepository subjectRepository;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public LoadDto findById(Long id) {
        Optional<LoadEntity> loadEntity = loadRepository.findById(id);
        if (loadEntity.isPresent()) {
            LoadDto loadDto = loadEntity.get().getDto();
            FieldDto fieldDto = new FieldDto();
            if (Objects.nonNull(loadEntity.get().getLoadField())) {
                fieldDto.setId(loadEntity.get().getLoadField().getId());
                fieldDto.setFieldName(loadEntity.get().getLoadField().getFieldName());
                fieldDto.setFieldCode(loadEntity.get().getLoadField().getFieldCode());
            }
            loadDto.setLoadField(fieldDto);

            SubjectDto subDto = new SubjectDto();
            if (Objects.nonNull(loadEntity.get().getLoadSubject())) {
                subDto.setId(loadEntity.get().getLoadSubject().getId());
                subDto.setSubjectName(loadEntity.get().getLoadSubject().getSubjectName());
                subDto.setSubjectBall(loadEntity.get().getLoadSubject().getSubjectBall());
            }
            loadDto.setLoadSubject(subDto);

            return loadDto;
        }
        throw new TableEntityNotFoundException("Load not found by ID[" + id + "] exception");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<LoadDto> getLoadDataTables(Long subjectId, FilterForm filter) {
//        System.out.println(subjectId + " fan id boku!");
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<LoadEntity> loadEntityPage = loadRepository.findBySubjectId(subjectId, pageRequest);

        List<LoadDto> loadDtos = loadEntityPage.get()
                .map(LoadEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<LoadDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) loadEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) loadEntityPage.getTotalElements());
        dataTablesForm.setData(loadDtos);

        return dataTablesForm;
    }

    @Transactional(readOnly = true)
    public List<LoadBySubjectsDto> getLoadTotalHours() {
        List<LoadBySubjectsDto> loadEntityPage = loadBySubjectsRepository.getLoadBySubjectId();
        return loadEntityPage;
    }

    @Transactional
    public void saveBackendLoad(LoadDto loadDto, boolean isProfile) {
        LoadEntity loadEntity = new LoadEntity();
        BeanUtils.copyProperties(loadDto, loadEntity);
        if (Objects.nonNull(loadDto.getLoadField()) && Objects.nonNull(loadDto.getLoadField().getId()) )
            loadEntity.setFieldId(loadDto.getLoadField().getId());
        if (Objects.nonNull(loadDto.getLoadSubject()) && Objects.nonNull(loadDto.getLoadSubject().getId()) )
            loadEntity.setSubjectId(loadDto.getLoadSubject().getId());
        loadRepository.save(loadEntity);
    }

    @Transactional
    public String editBackendLoad(LoadDto loadDto, BindingResult bindingResult, boolean isProfile) {
        LoadEntity loadEntity = new LoadEntity();
        if (loadDto.getId() != null) {
            Optional<LoadEntity> loadEntityOptional = loadRepository.findById(loadDto.getId());
            if (loadEntityOptional.isPresent()) {
                loadEntity = loadEntityOptional.get();
                if (isProfile)
                    return "loads/profile";
            } else {
                throw new TableEntityNotFoundException("Load not found by  id[" + loadDto.getId() + "] exception!");
            }
        } else {
            throw new TableEntityNotFoundException("Load not found exception!");
        }
        BeanUtils.copyProperties(loadDto, loadEntity);
        if (Objects.nonNull(loadDto.getLoadSubject()) && Objects.nonNull(loadDto.getLoadSubject().getId()) )
            loadEntity.setSubjectId(loadDto.getLoadSubject().getId());
        if (Objects.nonNull(loadDto.getLoadField()) && Objects.nonNull(loadDto.getLoadField().getId()) )
            loadEntity.setFieldId(loadDto.getLoadField().getId());
        loadRepository.save(loadEntity);

        return isProfile ? "redirect:/dashboard/main.do" : "redirect:/loads/list.do/" + loadDto.getLoadSubject().getId();
    }

    @Transactional(readOnly = true)
    public List<LoadDto> getList() {
        return loadRepository.findAll()
                .stream()
                .map(LoadEntity::getDto)
                .collect(Collectors.toList());
    }
}

