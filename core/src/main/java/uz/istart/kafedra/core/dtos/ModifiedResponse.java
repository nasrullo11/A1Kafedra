package uz.istart.kafedra.core.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ModifiedResponse implements Serializable {

    private Boolean error;
    private String Message;
    private Object payload;
}
