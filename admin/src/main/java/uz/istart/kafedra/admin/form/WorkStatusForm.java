package uz.istart.kafedra.admin.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class WorkStatusForm implements Serializable {
    private Long entityId;
    private boolean enable;
    private Long statusId;
}
