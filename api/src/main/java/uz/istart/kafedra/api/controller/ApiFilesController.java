package uz.istart.kafedra.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.istart.kafedra.api.services.ApiFileRestService;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.entities.UserEntity;
import uz.istart.kafedra.core.repositories.UserRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class ApiFilesController {

    private final ApiFileRestService apiFileRestService;
    private final UserRepository userRepository;

    @PostMapping(value = "/upload.do")
    public @ResponseBody
    HashMap<String, String> handleFileUpload(@Valid MultipartFile file, Long userId, String fileName) {
        System.out.println(" 15151");
//        System.out.println(userImageForm.getUserId() + " +++++++++++++++ " + userImageForm.getFileName());
        HashMap<String, String> hm = new HashMap<>();
        if(file == null){
            System.out.println("nullllllllllllllllll");
        }

        if(userId == null)
        {
            System.out.println("userid null");
        }
//        if(backendFileService.isHasFileName(userImageForm.getFile().getFileName())){
//            throw new FileHasNameException(userImageForm.getFile().getOriginalFilename() + " --- Такой имя ФАЙЛ существует в системе!");
//        }
//        if (userImageForm.getFile().isEmpty()) {
//            hm.put("statusString", "Загруженный фото Пользаватель не должен быть пустым!");
//            throw new FileMustDoesNotEmptyException("Загруженный фото Пользаватель не должен быть пустым!");
//        }
//        if (StringUtils.isEmpty(userImageForm.getFile().getOriginalFilename())) {
//            hm.put("statusString", "Загруженный фото Пользаватель не должен быть пустым!");
//            throw new FileMustDoesNotEmptyException("Загруженный фото Пользаватель не должен быть пустым!");
//        }
//        if(userImageForm.getFile().getContentType().indexOf("jpeg")==-1
//                && userImageForm.getFile().getContentType().indexOf("jpg")==-1
//                && userImageForm.getFile().getContentType().indexOf("bmp")==-1
//                && userImageForm.getFile().getContentType().indexOf("pipeg")==-1
//                && userImageForm.getFile().getContentType().indexOf("gif")==-1
//                && userImageForm.getFile().getContentType().indexOf("png")==-1) {
//            hm.put("statusString", "JPG");
//            throw new FileMustOnlyImageFormatException("Загруженный файл формат должен быть рисунком. .PNG, JPEG, JPG, GIF, .BMP ..!");
//        }
        hm.put("statusString", "success");
        apiFileRestService.store(file, userId, fileName);

        return hm;
    }

    @GetMapping(value = "/get.do")
    public @ResponseBody
    String getFilePath(Long userId){
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if(optionalUserEntity.isPresent()){
            UserDto userDto = optionalUserEntity.get().getDto();
            return userDto.getFileLogo().getPath();
        }
        else {
            return "null";
        }
    }

    @GetMapping(value = "/get/{userId}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Long userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if(optionalUserEntity.isPresent()){
            UserDto userDto = optionalUserEntity.get().getDto();
            Long id = userDto.getFileLogo().getId();
            Resource file = apiFileRestService.loadAsResource(id);
            try {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                        .body(file);
            }
            catch (Exception e) {
                return null;
            }
        }
        else {
            return null;
        }
    }

}
