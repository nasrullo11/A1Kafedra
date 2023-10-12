package uz.istart.kafedra.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.istart.kafedra.api.dto.ApiUserRequestDto;
import uz.istart.kafedra.api.dto.ApiUserResponseDto;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.entities.UserEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by babayev.xushnud@gmail.com on 2/23/2021.
 * Project kafedra-app
 */

@Service
public class ApiUsersRestService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder2;

    public ApiUserResponseDto signIn(ApiUserRequestDto loginRequest) {

        String encodedPassword = passwordEncoder().encode(loginRequest.getPassword());
        System.out.println(loginRequest.getPassword() + " ----- " + loginRequest.getUsername());
        Optional<UserEntity> userEntity = userRepository.findByUsernameIgnoreCase(loginRequest.getUsername());
        System.out.println(userEntity.isPresent());
        UserDto userDto = new UserDto();

        if (userEntity.isPresent()) {
            userDto = userEntity.get().getDto();
            System.out.println(userDto.getUsername() + " +++ " + userDto.getFullName());
            return ApiUserResponseDto.builder()
                    .id(userDto.getId())
                    .username(userDto.getUsername())
                    .fullName(userDto.getFullName())
                    .department(userDto.getDepartment())
                    .token(userDto.getToken())
                    .type("Bearer")
                    .roles(userDto.getRole())
                    .status(true)
                    .message("success").build();
        } else return new ApiUserResponseDto();

    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            return userEntity.get().getDto();
        }
        throw new TableEntityNotFoundException("User not fount by  id[" + id + "] exception!");
    }

    @Transactional(readOnly = true)
    public UserDto findByUsernameIgnoreCase(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameIgnoreCase(username);

        if (userEntity.isPresent()) {
            return userEntity.get().getDto();
        }
        throw new TableEntityNotFoundException("User not fount by  username[" + username + "] exception!");
    }

    @Transactional(readOnly = true)
    public Page<UserEntity> findAll(Pageable pageable) {
        Page<UserEntity> users = userRepository.findAll(pageable);
        return users;
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public void editUser(UserEntity userEntity, String password, String fullName, String username) {
        userEntity.setPassword(passwordEncoder2.encode(password));
        userEntity.setFullName(fullName);
        if(!username.equalsIgnoreCase(userEntity.getUsername()))
            userEntity.setUsername(username);
        userRepository.save(userEntity);
    }

}
