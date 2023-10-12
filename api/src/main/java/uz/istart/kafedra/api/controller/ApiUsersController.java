package uz.istart.kafedra.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.istart.kafedra.api.dto.ApiUserRequestDto;
import uz.istart.kafedra.api.dto.ApiUserResponseDto;
import uz.istart.kafedra.api.services.ApiUsersRestService;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.entities.UserEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.UserRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by babayev.xushnud@gmail.com on 2/23/2021.
 * Project kafedra-app
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class ApiUsersController {

    private final ApiUsersRestService apiUsersRestService;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/signIn")
    @ResponseBody
    public ApiUserResponseDto authenticateUser(@Valid @RequestBody ApiUserRequestDto loginRequest) {
        System.out.println(loginRequest.getUsername() + "+-+-+-+-+" + loginRequest.getPassword());
        System.out.println(apiUsersRestService.signIn(loginRequest).toString());
        return apiUsersRestService.signIn(loginRequest);
    }

    @GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    UserDto getUser(@PathVariable("id") Long id) {

        return apiUsersRestService.findById(id);
    }


    @GetMapping(value = "/listAll")
    @ResponseBody
    public Page<UserDto> getUsers(Pageable pageable) {
        int totalElements = 0;
        List<UserDto> usersResponseDto = new ArrayList<UserDto>();
        Page<UserEntity> users = apiUsersRestService.findAll(pageable);

        if (users != null) {
            totalElements = users.getNumberOfElements();

            for (UserEntity user : users) {
                UserDto returnValue = new UserDto();
                BeanUtils.copyProperties(user, returnValue);
                usersResponseDto.add(returnValue);
            }
        }
        return new PageImpl<>(usersResponseDto, pageable, totalElements);
    }

    @GetMapping(value = "/listAllUsers")
    @ResponseBody
    public List<UserDto> getAllUsers() {
        List<UserDto> usersResponseDto = new ArrayList<UserDto>();
        List<UserEntity> users = apiUsersRestService.findUsers();

        if (users != null) {
            for (UserEntity user : users) {
                UserDto returnValue = new UserDto();
                BeanUtils.copyProperties(user, returnValue);
                usersResponseDto.add(returnValue);
            }
        }
        return usersResponseDto;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public HashMap<String, String> edit(Long userId, String username, String oldPassword, String newPassword, String fullName){
        UserEntity userEntity = new UserEntity();
        System.out.println(userId + " +-+ " + username + " +-+ " + oldPassword + " +-+ " + newPassword + " +-+ " + fullName);
        HashMap<String, String> hm = new HashMap<>();
        try{
            Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
            if (userEntityOptional.isPresent()) {
                userEntity = userEntityOptional.get();
            } else {
                throw new TableEntityNotFoundException("User not found exception!");
            }
            if (!bCryptPasswordEncoder.matches(oldPassword, userEntity.getPassword())) {
                hm.put("status", "error");
                hm.put("errorOldPassword", "Joriy parol xato!");
            }
            else {
                boolean b = false;
                List<UserEntity> userList = userRepository.findAll();
                for(UserEntity user : userList){
                    if(username.equalsIgnoreCase(user.getUsername()) && !username.equalsIgnoreCase(userEntity.getUsername())){
                        hm.put("status", "error");
                        hm.put("errorLogin", "Bunday login band!");
                        b = true;
                        break;
                    }
                }
                if(!b) {
                    apiUsersRestService.editUser(userEntity, newPassword, fullName, username);
                    hm.put("status", "success");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            hm.put("status", "errorServer");
            hm.put("errorServer", "Serverda xatolik yuz berdi!");
        }
        return hm;
    }



}
