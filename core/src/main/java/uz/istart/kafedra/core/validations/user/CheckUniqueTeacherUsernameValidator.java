package uz.istart.kafedra.core.validations.user;

import uz.istart.kafedra.core.dtos.TeacherDto;
import uz.istart.kafedra.core.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckUniqueTeacherUsernameValidator implements ConstraintValidator<CheckUniqueTeacherUsername, TeacherDto> {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public boolean isValid(TeacherDto teacherDto, ConstraintValidatorContext constraintValidatorContext) {
        if(teacherDto.getId() == null && teacherRepository.findByTeacherUsernameIgnoreCase(teacherDto.getTeacherUsername()).isPresent()){
            return false;
        }
        return true;
    }
}
