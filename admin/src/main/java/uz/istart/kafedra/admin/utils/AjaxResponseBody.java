package uz.istart.kafedra.admin.utils;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import uz.istart.kafedra.core.constants.Views;
import uz.istart.kafedra.core.dtos.DistributionByUsersDto;
import uz.istart.kafedra.core.dtos.UserDto;

@Getter
@Setter
public class AjaxResponseBody {
    @JsonView(Views.Public.class)
    String msg;
    @JsonView(Views.Public.class)
    String code;
    @JsonView(Views.Public.class)
    UserDto result;

    @JsonView(Views.Public.class)
    String username;

    @JsonView(Views.Public.class)
    Integer totalHour;

    @JsonView(Views.Public.class)
    DistributionByUsersDto disResult;

    @Override
    public String toString() {
        return "AjaxResponseResult [msg=" + msg + ", code=" + code + ", result=" + result + "]";
    }
}
