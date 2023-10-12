package uz.istart.kafedra.admin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import uz.istart.kafedra.admin.form.ActiveObjectForm;
import uz.istart.kafedra.admin.form.tables.DataTablesForm;
import uz.istart.kafedra.admin.form.tables.FilterForm;

import uz.istart.kafedra.core.constants.Role;
import uz.istart.kafedra.core.dtos.FileDto;
import uz.istart.kafedra.core.dtos.TeacherDto;
import uz.istart.kafedra.core.entities.TeacherEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.TeacherRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendTeacherService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private BackendMailSender mailSender;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public TeacherDto findById(Long id) {
        Optional<TeacherEntity> teacherEntity = teacherRepository.findById(id);
        if (teacherEntity.isPresent()) {
            TeacherDto teacherDto = teacherEntity.get().getDto();
            FileDto fileDto = new FileDto();
            if (Objects.nonNull(teacherEntity.get().getTeacherFileLogo())) {
                fileDto.setPath(teacherEntity.get().getTeacherFileLogo().getPath());
                fileDto.setFileName(teacherEntity.get().getTeacherFileLogo().getFileName());
                fileDto.setOrgFileName(teacherEntity.get().getTeacherFileLogo().getOrgFileName());
            }
            teacherDto.setTeacherFileLogo(fileDto);

            return teacherDto;
        }
        throw new TableEntityNotFoundException("Teacher not found by ID[" + id + "] exception");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<TeacherDto> getTeacherDataTables(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<TeacherEntity> teacherEntityPage = teacherRepository.findAll(pageRequest);

        List<TeacherDto> teacherDtos = teacherEntityPage.get()
                .map(TeacherEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<TeacherDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) teacherEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) teacherEntityPage.getTotalElements());
        dataTablesForm.setData(teacherDtos);

        return dataTablesForm;
    }
    
    @Transactional
    public void saveBackendTeacher(TeacherDto teacherDto) {
        TeacherEntity teacherEntity = new TeacherEntity();
        BeanUtils.copyProperties(teacherDto, teacherEntity, "teacherPassword");
        teacherEntity.setTeacherPassword((bCryptPasswordEncoder.encode("123456ag")));
        teacherEntity.setTeacherSurname("defaultSurname");
        if (Objects.nonNull(teacherDto.getTeacherFileLogo()) && Objects.nonNull(teacherDto.getTeacherFileLogo().getId()))
            teacherEntity.setTeacherFileLogoId(teacherDto.getTeacherFileLogo().getId());
        teacherRepository.save(teacherEntity);
    }

    @Transactional
    public String editBackendTeacher(TeacherDto teacherDto, BindingResult bindingResult, boolean isProfile) {
        TeacherEntity teacherEntity = new TeacherEntity();
        if (teacherDto.getId() != null) {
            Optional<TeacherEntity> teacherEntityOptional = teacherRepository.findById(teacherDto.getId());
            if (teacherEntityOptional.isPresent()) {
                teacherEntity = teacherEntityOptional.get();
                if (isProfile)
                    if (!bCryptPasswordEncoder.matches(teacherDto.getTeacherPassword(), teacherEntity.getTeacherPassword())) {
                        bindingResult.addError(new FieldError("teacher", "teacherPassword", "Wrong password!"));
                        return "teachers/profile";
                    }
            } else {
                throw new TableEntityNotFoundException("Teacher not fount by  id[" + teacherDto.getId() + "] exception!");
            }
        } else {
            throw new TableEntityNotFoundException("Teacher not fount exception!");
        }
        BeanUtils.copyProperties(teacherDto, teacherEntity, "teacherUsername", "teacherPassword");
        if (isProfile) {
            teacherEntity.setTeacherPassword(bCryptPasswordEncoder.encode(teacherDto.getTeacherNewPassword()));
        }
        if (Objects.nonNull(teacherDto.getTeacherFileLogo()) && Objects.nonNull(teacherDto.getTeacherFileLogo().getId())) {
            teacherEntity.setTeacherFileLogoId(teacherDto.getTeacherFileLogo().getId());
        }
        teacherRepository.save(teacherEntity);

        return isProfile ? "redirect:/dashboard/main.do" : "redirect:/teachers/list.do";
    }

    @Transactional(readOnly = true)
    public List<TeacherDto> getList() {
        return teacherRepository.findAll()
                .stream()
                .map(TeacherEntity::getDto)
                .collect(Collectors.toList());
    }

    private void sendMessage(TeacherEntity teacher) {

        if (!StringUtils.isEmpty(teacher.getTeacherUsername())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Kafedra. Please, visit next link: http://%s/teachers/view.do/%s",
                    teacher.getTeacherUsername(),
                    hostname,
                    teacher.getId()
            );

            mailSender.send(teacher.getTeacherUsername(), "Teacher status is Active/Deactive", message);
        }
    }
}
