package uz.istart.kafedra.core.dtos;

import lombok.Getter;
import lombok.Setter;
import uz.istart.kafedra.core.validations.user.CheckUniqueUsername;

import java.io.Serializable;

@Getter
@Setter
public class FieldDto implements Serializable{

    private Long id;
    private String fieldName;
    private String fieldCode;
}
