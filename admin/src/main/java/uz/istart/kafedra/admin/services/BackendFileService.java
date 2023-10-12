package uz.istart.kafedra.admin.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.istart.kafedra.admin.form.user.UserImageForm;
import uz.istart.kafedra.core.constants.FileStoreEnum;
import uz.istart.kafedra.core.entities.FileEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.exceptions.file.FileStorageException;
import uz.istart.kafedra.core.repositories.FileRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackendFileService {

    private final FileRepository fileRepository;

    @Value("${file.store.path}")
    private String fileStorePath;

    private Path fileLocation;


    @PostConstruct
    public void init(){
        File directory = new File(fileStorePath + File.separator + FileStoreEnum.User.getFolder());
        if (!directory.exists()) {
            directory.setExecutable(true, false);
            directory.setReadable(true, false);
            directory.setWritable(true, false);
            directory.mkdir();
        }
        fileLocation = Paths.get(directory.getPath());
    }

    @Transactional
    public Long store(UserImageForm userImageForm) {
        MultipartFile file = userImageForm.getFile();
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }
        long size = file.getSize();
        String newFilename = UUID.randomUUID().toString()+"."+extension;
        try {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.fileLocation.resolve(newFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new FileStorageException(filename + " rasmni xotiraga saqlashda xatolik yuzaga keldi!");
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(newFilename);
        fileEntity.setOrgFileName(filename);
        fileEntity.setPath(fileLocation.toAbsolutePath().toString()+File.separator+newFilename);
        fileRepository.save(fileEntity);


        return fileEntity.getId();
    }

    @Transactional
    public Resource loadAsResource(Long id) {
        try {
            Optional<FileEntity> fileEntity = fileRepository.findById(id);
            if (fileEntity.isPresent()) {
                Path file = fileLocation.resolve(fileEntity.get().getFileName());
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new TableEntityNotFoundException("Bunday ID raqamli rasm mavjud emas!");
                }
            } else {
                throw new TableEntityNotFoundException("Bunday ID raqamli rasm mavjud emas!");
            }
        } catch (MalformedURLException e) {
            throw new TableEntityNotFoundException("Bunday ID raqamli rasm mavjud emas!");
        }
    }

    @Transactional(readOnly = true)
    public boolean isHasFileName(String name) {
        return fileRepository.findByOrgFileNameIgnoreCase(name).isPresent();
    }
}
