package uz.istart.kafedra.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uz.istart.kafedra.admin.config.security.AdminAuthenticationHandler;
import uz.istart.kafedra.admin.config.security.AdminUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
    private static String[] NOT_FILTER = new String[]{
            "/",
            "login.do*",
            "/login",
            "/content/view/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(backendUserDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**","/flag-icon-css-master/**", "/js/**", "/media/**", "/img/**","/fonts/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/ajax/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.do")
                .usernameParameter("username").passwordParameter("password").permitAll()
                .loginProcessingUrl("/login")
                .successHandler(new AdminAuthenticationHandler())
                .failureForwardUrl("/login.do?error=true")
                .and()
                .logout().logoutUrl("/logout.do").logoutSuccessUrl("/login.do?logout=true")
                .and()
                .exceptionHandling().accessDeniedPage("/login.do?denied=true");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("backendUserDetailsService")
    public UserDetailsService backendUserDetailsService(){
        return new AdminUserDetailsService();
    }
}
