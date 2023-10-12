package uz.istart.kafedra.core.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.FileDto;
import uz.istart.kafedra.core.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = TableNames.files)
public class FileEntity extends BaseEntity {
    @Column(name = "path")
    private String path;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "orgFileName")
    private String orgFileName;

    public FileDto getDto(){
        FileDto fileDto = new FileDto();
        BeanUtils.copyProperties(this,fileDto);

        return fileDto;
    }

}
