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
import uz.istart.kafedra.core.entities.FieldEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.FieldRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendFieldService {
    private final FieldRepository fieldRepository;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public FieldDto findById(Long id) {
        Optional<FieldEntity> fieldEntity = fieldRepository.findById(id);
        if (fieldEntity.isPresent()) {
            FieldDto fieldDto = fieldEntity.get().getDto();
            return fieldDto;
        }
        throw new TableEntityNotFoundException("Field not found by ID[" + id + "] exception");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<FieldDto> getFieldDataTables(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<FieldEntity> fieldEntityPage = fieldRepository.findAll(pageRequest);

        List<FieldDto> fieldDtos = fieldEntityPage.get()
                .map(FieldEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<FieldDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) fieldEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) fieldEntityPage.getTotalElements());
        dataTablesForm.setData(fieldDtos);

        return dataTablesForm;
    }

    @Transactional
    public void saveBackendField(FieldDto fieldDto) {
        FieldEntity fieldEntity = new FieldEntity();
        BeanUtils.copyProperties(fieldDto, fieldEntity);
        if (Objects.nonNull(fieldDto.getFieldCode()))
            fieldEntity.setFieldCode(fieldEntity.getFieldCode());
        if (Objects.nonNull(fieldDto.getFieldName()))
            fieldEntity.setFieldName(fieldDto.getFieldName());
        fieldRepository.save(fieldEntity);
    }

    @Transactional
    public String editBackendField(FieldDto fieldDto, BindingResult bindingResult, boolean isProfile) {
        FieldEntity fieldEntity = new FieldEntity();
        if (fieldDto.getId() != null) {
            Optional<FieldEntity> fieldEntityOptional = fieldRepository.findById(fieldDto.getId());
            if (fieldEntityOptional.isPresent()) {
                fieldEntity = fieldEntityOptional.get();
                if (isProfile)
                    return "fields/profile";
            } else {
                throw new TableEntityNotFoundException("Group not found by  id[" + fieldDto.getId() + "] exception!");
            }
        } else {
            throw new TableEntityNotFoundException("Group not found exception!");
        }
        BeanUtils.copyProperties(fieldDto, fieldEntity);
        if (Objects.nonNull(fieldDto.getFieldName()))
            fieldEntity.setFieldName(fieldDto.getFieldName());
        if (Objects.nonNull(fieldDto.getFieldCode()))
            fieldEntity.setFieldCode(fieldDto.getFieldCode());
        fieldRepository.save(fieldEntity);

        return isProfile ? "redirect:/dashboard/main.do" : "redirect:/fields/list.do";
    }

    @Transactional
    public void deleteField(Long id) {
        if (id != null) {
            Optional<FieldEntity> disEntity = fieldRepository.findById(id);
            if (disEntity.isPresent()) {
                fieldRepository.delete(disEntity.get());
            } else {
                throw new TableEntityNotFoundException(id + " ID ga ega yo'nalish topilmadi!");
            }
        } else {
            throw new TableEntityNotFoundException("Yo'nalish topilmadi!");
        }
    }

    @Transactional(readOnly = true)
    public List<FieldDto> getList() {
        return fieldRepository.findAll()
                .stream()
                .map(FieldEntity::getDto)
                .collect(Collectors.toList());
    }
}
