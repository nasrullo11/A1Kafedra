package uz.istart.kafedra.admin.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.istart.kafedra.admin.form.user.UserImageForm;
import uz.istart.kafedra.admin.services.BackendFileService;
import uz.istart.kafedra.core.exceptions.file.FileMustDoesNotEmptyException;
import uz.istart.kafedra.core.exceptions.file.FileMustOnlyImageFormatException;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMINISTRATOR','MANAGER', 'Kafedra_mudiri', 'Uqituvchi')")
public class AdminFileController {

    private final BackendFileService backendFileService;

    @PostMapping(value = "/upload.do")
    public @ResponseBody
    Long handleFileUpload(@Valid UserImageForm userImageForm, BindingResult bindingResult) {

//        if(backendFileService.isHasFileName(userImageForm.getFile().getFileName())){
//            throw new FileHasNameException(userImageForm.getFile().getOriginalFilename() + " --- Такой имя ФАЙЛ существует в системе!");
//        }
        if (userImageForm.getFile().isEmpty()) {
            throw new FileMustDoesNotEmptyException("Загруженный фото Пользаватель не должен быть пустым!");
        }
        if (StringUtils.isEmpty(userImageForm.getFile().getOriginalFilename())) {
            throw new FileMustDoesNotEmptyException("Загруженный фото Пользаватель не должен быть пустым!");
        }
        if(userImageForm.getFile().getContentType().indexOf("jpeg")==-1
                && userImageForm.getFile().getContentType().indexOf("jpg")==-1
                && userImageForm.getFile().getContentType().indexOf("bmp")==-1
                && userImageForm.getFile().getContentType().indexOf("pipeg")==-1
                && userImageForm.getFile().getContentType().indexOf("gif")==-1
                && userImageForm.getFile().getContentType().indexOf("png")==-1) {
            throw new FileMustOnlyImageFormatException("Загруженный файл формат должен быть рисунком. .PNG, JPEG, JPG, GIF, .BMP ..!");
        }
        return backendFileService.store(userImageForm);
    }

    @GetMapping(value = "/get/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {

        Resource file = backendFileService.loadAsResource(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
