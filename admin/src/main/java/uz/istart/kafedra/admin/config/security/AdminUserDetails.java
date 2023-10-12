package uz.istart.kafedra.admin.config.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import uz.istart.kafedra.core.constants.Role;

import java.util.Collection;

@Getter
public class AdminUserDetails extends User {

    private Long userId;
    private String fullName;
    private String username;
    private Role role;

    public AdminUserDetails(String username, String password, Long userId, String fullName, Role role, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.fullName = fullName;
        this.role = role;
    }
}
