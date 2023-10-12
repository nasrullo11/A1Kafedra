package uz.istart.kafedra.core.constants;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMINISTRATOR,
    MANAGER,
    Kafedra_mudiri,
    Uqituvchi;


    @Override
    public String getAuthority() {
        return null;
    }
}
