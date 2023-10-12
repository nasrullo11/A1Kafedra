package uz.istart.kafedra.core.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class WorkTypeDto implements Serializable{

    private Long id;
    private String workTypeName;
}
