package uz.istart.kafedra.api.dto;

import lombok.*;
import uz.istart.kafedra.core.constants.Role;
import uz.istart.kafedra.core.dtos.DepartmentDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserResponseDto {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String fullName;
    private Role roles;
    private DepartmentDto department;
    private boolean status = false;
    private String message = "Login yoki parol xato!";



    @Override
    public String toString() {
        return "ApiUserResponseDto{" +
                "token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", email='" + fullName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
