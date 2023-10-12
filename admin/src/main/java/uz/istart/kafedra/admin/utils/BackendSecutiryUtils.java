package uz.istart.kafedra.admin.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.istart.kafedra.admin.config.security.AdminUserDetails;

public class BackendSecutiryUtils {

    public static Long getUserId(){
        if(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            if(token.getPrincipal() instanceof AdminUserDetails){
                AdminUserDetails userDetails = (AdminUserDetails) token.getPrincipal();
                return userDetails.getUserId();
            }
        }
        return null;
    }

    public static AdminUserDetails getUserDetails(){
        if(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            if(token.getPrincipal() instanceof AdminUserDetails){
                return (AdminUserDetails) token.getPrincipal();
            }
        }
        return null;
    }
}
