package uz.istart.kafedra.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
 }
