package uz.istart.kafedra.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.istart.kafedra.core.constants.FileStoreEnum;
import uz.istart.kafedra.core.entities.FileEntity;
import uz.istart.kafedra.core.entities.UserEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.exceptions.file.FileStorageException;
import uz.istart.kafedra.core.repositories.FileRepository;
import uz.istart.kafedra.core.repositories.UserRepository;

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

@Service
public class ApiFileRestService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    UserRepository userRepository;

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
    public Long store(MultipartFile file, Long userId, String fileName) {
        System.out.println(" 121 " + file.getSize() + " 212 " + file.getOriginalFilename());
        String filename = StringUtils.cleanPath(fileName); // Bir tekshirib go'rali shuni!!!
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }
        long size = file.getSize();
        String newFilename = UUID.randomUUID().toString()+".jpg";
        try {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.fileLocation.resolve(newFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new FileStorageException(e.getMessage() + " rasmni xotiraga saqlashda xatolik yuzaga keldi!");
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(newFilename);
        fileEntity.setOrgFileName(filename);
        fileEntity.setPath(fileLocation.toAbsolutePath().toString()+File.separator+newFilename);
        System.out.println("store method ishladi ++++++++++++++++++++++");
        fileRepository.save(fileEntity);

        UserEntity userEntity = new UserEntity();
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isPresent()) {
            userEntity = userEntityOptional.get();
        } else {
            throw new TableEntityNotFoundException("User not found exception!");
        }
        userEntity.setFileLogoId(fileEntity.getId());
        userRepository.save(userEntity);
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
