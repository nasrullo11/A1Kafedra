package uz.istart.kafedra.core.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import uz.istart.kafedra.core.constants.Views;

public interface DistributionByUsersDto {

    @JsonView(Views.Public.class)
    Long getUserId();

    @JsonView(Views.Public.class)
    String getUsername();

    @JsonView(Views.Public.class)
    String getFullName();

    @JsonView(Views.Public.class)
    Integer getTotalHour();
}
