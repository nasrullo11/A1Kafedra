package uz.istart.kafedra.admin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.istart.kafedra.admin.form.ActiveObjectForm;
import uz.istart.kafedra.admin.form.OkResponseForm;
import uz.istart.kafedra.admin.form.tables.DataTablesForm;
import uz.istart.kafedra.admin.form.tables.FilterForm;
import uz.istart.kafedra.admin.services.BackendDepartmentService;
import uz.istart.kafedra.admin.services.BackendUserService;
import uz.istart.kafedra.admin.utils.BackendSecutiryUtils;
import uz.istart.kafedra.core.dtos.DepartmentDto;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'Kafedra_mudiri', 'Uqituvchi')")
public class AdminUsersController {

    private final BackendUserService backendUserService;
    private final BackendDepartmentService backendDepartmentService;

    @GetMapping("/list.do")
    public String list() {
        return "users/list";
    }

    @RequestMapping(value = "/user-list.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<UserDto> getUserList(@RequestBody FilterForm filter) {
        return backendUserService.getUserDataTables(filter);
    }

    @GetMapping("/listByDepartment.do")
    public String listByDepartment() {
        return "users/listByDepartment";
    }

    @RequestMapping(value = "/user-list-by-department.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<UserDto> getUserListByDepartment(@RequestBody FilterForm filter) {
        return backendUserService.getUsersByDepartment(filter);
    }

    @GetMapping("/view.do/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        UserDto userDto = backendUserService.findById(id);
        System.out.println(userDto.getFileLogo().getId());
        model.addAttribute("user", userDto);
        return "users/view";
    }
//    @RequestMapping(value = "/view-by-id.json/{id}", headers = {"Accept=application/json", "Content-type=application/json"})
//    public @ResponseBody
//    UserDto viewJson(@PathVariable("id") Long id) {
//        UserDto userDto = backendUserService.findById(id);
//        return userDto;
//    }

    @GetMapping("/profile.do")
    public String userSettingsGet(Model model) {
        Long userId = BackendSecutiryUtils.getUserId();
        UserDto userDto = backendUserService.findById(userId);
        model.addAttribute("user", userDto);
        return "users/profile";
    }

    @PostMapping("/profile.do")
    public String userSettingPost(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/profile";
        } else {
            if (StringUtils.isEmpty(userDto.getId())) {
                throw new TableEntityNotFoundException("User not found exception");
            } else {
                return backendUserService.editBackendUser(userDto, bindingResult, true);
            }
        }
    }

    @GetMapping("/edit.do/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        UserDto userDto = backendUserService.findById(id);
        model.addAttribute("user", userDto);
        List<DepartmentDto> depList = backendDepartmentService.getList();
        model.addAttribute("deps", depList);
        return "users/edit";
    }

    @PostMapping("/edit.do")
    public String editPost(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "users/edit";
        } else {
            if (StringUtils.isEmpty(userDto.getId())) {
                throw new TableEntityNotFoundException("User not found exception");
            } else {
                return backendUserService.editBackendUser(userDto, bindingResult, false);
            }
        }
    }

    @GetMapping("/create.do")
    public String createGet(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        List<DepartmentDto> depList = backendDepartmentService.getList();
        model.addAttribute("deps", depList);
        return "users/create";
    }

    @PostMapping("/create.do")
    public String createPost(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "users/create";
        } else {
            backendUserService.saveBackendUser(userDto);
        }
        return "redirect:/users/list.do";
    }

    @RequestMapping(value = "/active.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    OkResponseForm activeUser(@RequestBody ActiveObjectForm activeObjectForm) {
        backendUserService.activeOrDeactive(activeObjectForm);
        return new OkResponseForm();
    }
}
