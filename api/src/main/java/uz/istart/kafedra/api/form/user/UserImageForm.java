package uz.istart.kafedra.api.form.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
public class UserImageForm implements Serializable {

    private Long userId;
    //private String orgFileName;
    private String fileName;
    private MultipartFile file;
}
