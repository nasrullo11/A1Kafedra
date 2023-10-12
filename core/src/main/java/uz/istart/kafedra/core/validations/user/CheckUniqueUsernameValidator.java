package uz.istart.kafedra.core.validations.user;

import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckUniqueUsernameValidator implements ConstraintValidator<CheckUniqueUsername, UserDto> {

    @Autowired
    private UserRepository userRepository;

   @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        if(userDto.getId() == null && userRepository.findByUsernameIgnoreCase(userDto.getUsername()).isPresent()){
            return false;
        }
        return true;
    }

    @Override
    public void initialize(CheckUniqueUsername constraintAnnotation) {
        // Initialization, if needed
    }
}
