package uz.istart.kafedra.core.entities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.FieldDto;
import uz.istart.kafedra.core.dtos.LoadDto;
import uz.istart.kafedra.core.dtos.SubjectDto;
import uz.istart.kafedra.core.entities.base.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = TableNames.loads)
public class LoadEntity extends BaseEntity{

    @Column(name = "fieldId")
    private Long fieldId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fieldId",insertable = false,updatable = false)
    private FieldEntity loadField;

    @Column(name = "subjectId")
    private Long subjectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId",insertable = false,updatable = false)
    private SubjectEntity loadSubject;

    @Column(name = "loadHour")
    private Integer subjectHour;

    @Column(name = "semesterId")
    private Integer semester;

    public LoadDto getDto(){
        LoadDto loadDto = new LoadDto();
        BeanUtils.copyProperties(this,loadDto);
        if (getLoadField() != null){
            FieldDto field = new FieldDto();
            field.setId(getLoadField().getId());
            field.setFieldName(getLoadField().getFieldName());
            field.setFieldCode(getLoadField().getFieldCode());
            loadDto.setLoadField(field);
        }

        if (getLoadSubject() != null){
            SubjectDto subject = new SubjectDto();
            subject.setId(getLoadSubject().getId());
            subject.setSubjectName(getLoadSubject().getSubjectName());
            loadDto.setLoadSubject(subject);
        }

        return loadDto;
    }
}
