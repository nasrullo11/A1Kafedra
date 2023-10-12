package uz.istart.kafedra.core.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import uz.istart.kafedra.core.constants.Role;
import uz.istart.kafedra.core.constants.Views;
import uz.istart.kafedra.core.validations.user.CheckUniqueUsername;
import uz.istart.kafedra.core.validations.user.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@CheckUniqueUsername
@JsonInclude(NON_NULL)
public class UserDto implements Serializable {

    private Long id;

    private String token;

    @NotEmpty
    @JsonView(Views.Public.class)
    private String fullName;

    @NotEmpty(message = "Пожалуйста, введите аккоунт название пользователя")
    @JsonView(Views.Public.class)
    private String username;

    @ValidPassword
    private String password;

    @ValidPassword
    private String newPassword;

    @NotNull
    private Role role;

    private Boolean isActive;
    private LocalDateTime createDate;

    @JsonView(Views.Public.class)
    private FileDto fileLogo;

    private DepartmentDto department;
}
