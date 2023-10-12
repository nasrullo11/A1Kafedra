package uz.istart.kafedra.admin.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActiveObjectForm implements Serializable {
    private Long entityId;
    private boolean enable;
}
