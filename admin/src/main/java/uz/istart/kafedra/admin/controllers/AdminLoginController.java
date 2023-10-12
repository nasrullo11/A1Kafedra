package uz.istart.kafedra.admin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.istart.kafedra.admin.services.BackendUserService;
import uz.istart.kafedra.core.entities.DepartmentEntity;
import uz.istart.kafedra.core.repositories.DepartmentRepository;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminLoginController {

    private final BackendUserService backendUserService;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping(path = {"/", "/login.do"})
    public String login() {
        return "login/login";
    }

    @PostMapping("/login.do")
    public String loginPost(Model model, @RequestParam(name = "error", required = false) String error,
                            @RequestParam(name = "logout", required = false) String logout,
                            @RequestParam(name = "denied", required = false) String denied) {
        if (!StringUtils.isEmpty(error)) {
            System.out.println("Errorrrrrr: " + error);
            model.addAttribute("error", true);
        }
        if (!StringUtils.isEmpty(logout)) {
            model.addAttribute("logout", true);
        }
        if (!StringUtils.isEmpty(denied)) {
            System.out.println("Deniedddddd: " + denied);
            model.addAttribute("denied", true);
        }
        return "login/login";
    }

    @PostMapping("/logout.do")
    public String logout1() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login.do";
    }
}
