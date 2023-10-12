package uz.istart.kafedra.admin.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.istart.kafedra.core.entities.UserEntity;
import uz.istart.kafedra.core.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUsernameIgnoreCase(username);
        if (!userEntity.isPresent()) {
            throw new UsernameNotFoundException(username + " user not found!");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userEntity.get().getRole().name()));

        return new AdminUserDetails(
                userEntity.get().getUsername(),
                userEntity.get().getPassword(),
                userEntity.get().getId(),
                userEntity.get().getFullName(),
                userEntity.get().getRole(),
                grantedAuthorities);
    }
}
