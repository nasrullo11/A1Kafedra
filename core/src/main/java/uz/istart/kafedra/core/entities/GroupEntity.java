package uz.istart.kafedra.core.entities;
import uz.istart.kafedra.core.constants.TableNames;
import uz.istart.kafedra.core.dtos.GroupDto;
import uz.istart.kafedra.core.dtos.FieldDto;
import uz.istart.kafedra.core.dtos.EducationTypeDto;
import uz.istart.kafedra.core.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Entity
@Table(name = TableNames.groups)
public class GroupEntity extends BaseEntity {

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "entrance_year")
    private Integer entranceYear;

    @Column(name = "education_type_id")
    private Long educationTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_type_id",insertable = false,updatable = false)
    private EducationTypeEntity groupEducationType;

    @Column(name = "field_id")
    private Long fieldId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id",insertable = false,updatable = false)
    private FieldEntity groupField;

    @Column(name = "amount_students")
    private Integer amountStudents;

    public GroupDto getDto(){
        GroupDto groupDto = new GroupDto();
        BeanUtils.copyProperties(this,groupDto);
        if (getGroupEducationType() != null){
            EducationTypeDto educationType = new EducationTypeDto();
            educationType.setId(getGroupEducationType().getId());
            educationType.setEducationTypeName(getGroupEducationType().getEducationTypeName());
            groupDto.setGroupEducationType(educationType);
        }
        if (getGroupField() != null){
            FieldDto field = new FieldDto();
            field.setId(getGroupField().getId());
            field.setFieldName(getGroupField().getFieldName());
            field.setFieldCode(getGroupField().getFieldCode());
            groupDto.setGroupField(field);
        }
        return groupDto;
    }
}
