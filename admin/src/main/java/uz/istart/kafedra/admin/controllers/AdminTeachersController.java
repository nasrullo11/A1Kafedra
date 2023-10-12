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
import uz.istart.kafedra.admin.services.BackendTeacherService;
import uz.istart.kafedra.admin.utils.BackendSecutiryUtils;
import uz.istart.kafedra.core.dtos.TeacherDto;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class AdminTeachersController {

    private final BackendTeacherService backendTeacherService;
 
    @GetMapping("/list.do")
    public String list() {
        return "teachers/list";
    }

    @GetMapping("/view.do/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        TeacherDto teacherDto = backendTeacherService.findById(id);
        model.addAttribute("teacher", teacherDto);
        return "teachers/view";
    }

    @GetMapping("/profile.do")
    public String teacherSettingsGet(Model model) {
        Long teacherId = BackendSecutiryUtils.getUserId();
        TeacherDto teacherDto = backendTeacherService.findById(teacherId);
        model.addAttribute("teacher", teacherDto);
        return "teachers/profile";
    }

    @PostMapping("/profile.do")
    public String teacherSettingPost(@ModelAttribute("teacher") @Valid TeacherDto teacherDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "teachers/profile";
        } else {
            if (StringUtils.isEmpty(teacherDto.getId())) {
                throw new TableEntityNotFoundException("Teacher not found exception");
            } else {
                return backendTeacherService.editBackendTeacher(teacherDto, bindingResult, true);
            }
        }
    }

    @GetMapping("/edit.do/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        TeacherDto teacherDto = backendTeacherService.findById(id);
        model.addAttribute("teacher", teacherDto);
        return "teachers/edit";
    }

    @PostMapping("/edit.do")
    public String editPost(@ModelAttribute("teacher") @Valid TeacherDto teacherDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "teachers/edit";
        } else {
            if (StringUtils.isEmpty(teacherDto.getId())) {
                throw new TableEntityNotFoundException("Teacher not found exception");
            } else {
                return backendTeacherService.editBackendTeacher(teacherDto, bindingResult, false);
            }
        }
    }

    @GetMapping("/create.do")
    public String createGet(Model model) {
        TeacherDto teacherDto = new TeacherDto();
        model.addAttribute("teacher", teacherDto);
        return "teachers/create";
    }

    @PostMapping("/create.do")
    public String createPost(@ModelAttribute("teacher") @Valid TeacherDto teacherDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        } else {
            backendTeacherService.saveBackendTeacher(teacherDto);
        }
        return "redirect:/teachers/list.do";
    }

    @RequestMapping(value = "/teacher-list.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<TeacherDto> getTeacherList(@RequestBody FilterForm filter) {
        return backendTeacherService.getTeacherDataTables(filter);
    }
}
