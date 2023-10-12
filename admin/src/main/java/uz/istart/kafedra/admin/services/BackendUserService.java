package uz.istart.kafedra.admin.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import uz.istart.kafedra.admin.config.security.AdminUserDetails;
import uz.istart.kafedra.admin.form.ActiveObjectForm;
import uz.istart.kafedra.admin.form.tables.DataTablesForm;
import uz.istart.kafedra.admin.form.tables.FilterForm;
import uz.istart.kafedra.core.constants.Role;
import uz.istart.kafedra.core.dtos.DepartmentDto;
import uz.istart.kafedra.core.dtos.FileDto;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.entities.UserEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.UserRepository;
import uz.istart.kafedra.core.specs.users.UsersByRoleSpecification;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private BackendMailSender mailSender;

    @Value("${hostname}")
    private String hostname;

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            UserDto userDto = userEntity.get().getDto();
            FileDto fileDto = new FileDto();
            if (Objects.nonNull(userEntity.get().getFileLogo())) {
                fileDto.setId(userEntity.get().getFileLogo().getId());
                fileDto.setPath(userEntity.get().getFileLogo().getPath());
                fileDto.setFileName(userEntity.get().getFileLogo().getFileName());
                fileDto.setOrgFileName(userEntity.get().getFileLogo().getOrgFileName());
            }
            userDto.setFileLogo(fileDto);
            DepartmentDto departmentDto = new DepartmentDto();
            if (Objects.nonNull(userEntity.get().getDepartment())) {
                departmentDto.setDepartmentName(userEntity.get().getDepartment().getDepartmentName());
            }
            userDto.setDepartment(departmentDto);

            return userDto;
        }
        throw new TableEntityNotFoundException(id + " ID ga ega foydalanuvchi topilmadi!");
    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameIgnoreCase(username);
        if (userEntity.isPresent()) {
            UserDto userDto = userEntity.get().getDto();
            FileDto fileDto = new FileDto();
            if (Objects.nonNull(userEntity.get().getFileLogo())) {
                fileDto.setId(userEntity.get().getFileLogo().getId());
                fileDto.setPath(userEntity.get().getFileLogo().getPath());
                fileDto.setFileName(userEntity.get().getFileLogo().getFileName());
                fileDto.setOrgFileName(userEntity.get().getFileLogo().getOrgFileName());
            }
            userDto.setFileLogo(fileDto);
            DepartmentDto departmentDto = new DepartmentDto();
            if (Objects.nonNull(userEntity.get().getDepartment())) {
                departmentDto.setDepartmentName(userEntity.get().getDepartment().getDepartmentName());
            }
            userDto.setDepartment(departmentDto);

            return userDto;
        }
        throw new TableEntityNotFoundException(username + " foydalanuvchi topilmadi!");
    }

    @Transactional(readOnly = true)
    public UserDto findByAuth() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity = userRepository.findById(((AdminUserDetails) principal).getUserId());
        if (userEntity.isPresent()) {
            UserDto userDto = userEntity.get().getDto();
            FileDto fileDto = new FileDto();
            if (Objects.nonNull(userEntity.get().getFileLogo())) {
                fileDto.setId(userEntity.get().getFileLogo().getId());
                fileDto.setPath(userEntity.get().getFileLogo().getPath());
                fileDto.setFileName(userEntity.get().getFileLogo().getFileName());
                fileDto.setOrgFileName(userEntity.get().getFileLogo().getOrgFileName());
            }
            userDto.setFileLogo(fileDto);
            DepartmentDto departmentDto = new DepartmentDto();
            if (Objects.nonNull(userEntity.get().getDepartment())) {
                departmentDto.setId(userEntity.get().getDepartment().getId());
                departmentDto.setDepartmentName(userEntity.get().getDepartment().getDepartmentName());
            }
            userDto.setDepartment(departmentDto);

            return userDto;
        }
        throw new TableEntityNotFoundException(((AdminUserDetails) principal).getUserId() + " foydalanuvchi topilmadi!");
    }

    @Transactional(readOnly = true)
    public DataTablesForm<UserDto> getUserDataTables(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        Page<UserEntity> userEntityPage = userRepository.findAll(
                new UsersByRoleSpecification(Arrays.asList(Role.Kafedra_mudiri, Role.Uqituvchi, Role.ADMINISTRATOR, Role.MANAGER), filter.getSearchTxt()), pageRequest);

        List<UserDto> userDtos = userEntityPage.get()
                .map(UserEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<UserDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) userEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) userEntityPage.getTotalElements());
        dataTablesForm.setData(userDtos);

        return dataTablesForm;
    }

    @Transactional(readOnly = true)
    public DataTablesForm<UserDto> getUsersByDepartment(FilterForm filter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        PageRequest pageRequest = PageRequest.of(filter.getStart() / filter.getLength(), filter.getLength(), sort);
        UserDto user = this.findByAuth();
        Page<UserEntity> userEntityPage = userRepository.findByDepartmentId(user.getDepartment().getId(), pageRequest);

        List<UserDto> userDtos = userEntityPage.get()
                .map(UserEntity::getDto)
                .collect(Collectors.toList());

        DataTablesForm<UserDto> dataTablesForm = new DataTablesForm<>();
        dataTablesForm.setDraw(filter.getDraw());
        dataTablesForm.setRecordsTotal((int) userEntityPage.getTotalElements());
        dataTablesForm.setRecordsFiltered((int) userEntityPage.getTotalElements());
        dataTablesForm.setData(userDtos);

        return dataTablesForm;
    }

    @Transactional
    public void saveBackendUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        UserDto user = this.findByAuth();
        BeanUtils.copyProperties(userDto, userEntity, "password", "isActive", "department");
        userEntity.setCreateDate(LocalDateTime.now());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getNewPassword()));
        userEntity.setDepartmentId(user.getDepartment().getId());
        if (Objects.nonNull(userDto.getFileLogo()) && Objects.nonNull(userDto.getFileLogo().getId()))
            userEntity.setFileLogoId(userDto.getFileLogo().getId());
        userRepository.save(userEntity);
    }

    @Transactional
    public String editBackendUser(UserDto userDto, BindingResult bindingResult, boolean isProfile) {
        UserEntity userEntity = new UserEntity();
        if (userDto.getId() != null) {
            Optional<UserEntity> userEntityOptional = userRepository.findById(userDto.getId());
            if (userEntityOptional.isPresent()) {
                userEntity = userEntityOptional.get();
                if (isProfile)
                    if (!bCryptPasswordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
                        bindingResult.addError(new FieldError("user", "password", "Wrong password!"));
                        return "users/profile";
                    }
            } else {
                throw new TableEntityNotFoundException(userDto.getId() + " ID ga ega foydalanuvchi topilmadi!");
            }
        } else {
            throw new TableEntityNotFoundException("Foydalanuvchi topilmadi!");
        }
        BeanUtils.copyProperties(userDto, userEntity, "isActive", "createDate", "department");
        if (!isProfile) {
            userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getNewPassword()));
        }
        UserDto user = this.findByAuth();
        userEntity.setDepartmentId(user.getDepartment().getId());
        if (Objects.nonNull(userDto.getFileLogo()) && Objects.nonNull(userDto.getFileLogo().getId()))
            userEntity.setFileLogoId(userDto.getFileLogo().getId());
        userEntity.setCreateDate(LocalDateTime.now());
        userRepository.save(userEntity);

        return isProfile ? "redirect:/dashboard/main.do" : "redirect:/users/list.do";
    }

    @Transactional
    public void activeOrDeactive(ActiveObjectForm activeObjectForm) {
        if (activeObjectForm.getEntityId() != null) {
            Optional<UserEntity> userEntity = userRepository.findById(activeObjectForm.getEntityId());
            if (userEntity.isPresent()) {
                userEntity.get().setIsActive(activeObjectForm.isEnable());
                userRepository.save(userEntity.get());
                sendMessage(userEntity.get());
            } else {
                throw new TableEntityNotFoundException(activeObjectForm.getEntityId() + " ID ga ega foydalanuvchi topilmadi!");
            }
        } else {
            throw new TableEntityNotFoundException("Foydalanuvchi topilmadi!");
        }
    }

    @Transactional(readOnly = true)
    public List<UserDto> getList() {
        return userRepository.findAll()
                .stream()
                .map(UserEntity::getDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDto> getListByDepartment(Long departmentId) {
        return userRepository.findByDepartmentId(departmentId)
                .stream()
                .map(UserEntity::getDto)
                .collect(Collectors.toList());
    }

    private void sendMessage(UserEntity user) {

        if (!StringUtils.isEmpty(user.getUsername())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Kafedra. Please, visit next link: http://%s/users/view.do/%s",
                    user.getUsername(),
                    hostname,
                    user.getId()
            );

            mailSender.send(user.getUsername(), "User status is Active/Deactive", message);
        }
    }
}
