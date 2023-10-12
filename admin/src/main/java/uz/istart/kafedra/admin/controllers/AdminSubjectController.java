package uz.istart.kafedra.admin.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.istart.kafedra.admin.form.OkResponseForm;
import uz.istart.kafedra.admin.form.tables.DataTablesForm;
import uz.istart.kafedra.admin.form.tables.FilterForm;
import uz.istart.kafedra.admin.services.BackendDepartmentService;
import uz.istart.kafedra.admin.services.BackendSubjectService;
import uz.istart.kafedra.core.dtos.DepartmentDto;
import uz.istart.kafedra.core.dtos.SubjectDto;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/subjects")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'Kafedra_mudiri', 'Uqituvchi')")
public class AdminSubjectController {

    private final BackendSubjectService backendSubjectService;
    private final BackendDepartmentService backendDepartmentService;

    @GetMapping("/list.do")
    public String list() {
        return "subjects/list";
    }

    @GetMapping("/view.do/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        SubjectDto subjectDto = backendSubjectService.findById(id);
        model.addAttribute("subject", subjectDto);
        return "subjects/view";
    }

    @GetMapping("/edit.do/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        SubjectDto subjectDto = backendSubjectService.findById(id);
        model.addAttribute("subject", subjectDto);
        List<DepartmentDto> departmentList = backendDepartmentService.getList();
        model.addAttribute("departments", departmentList);
        return "subjects/edit";
    }

    @PostMapping("/edit.do")
    public String editPost(@ModelAttribute("subject") @Valid SubjectDto subjectDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "subjects/edit";
        } else {
            if (StringUtils.isEmpty(subjectDto.getId())) {
                throw new TableEntityNotFoundException("Subject not found exception");
            } else {
                return backendSubjectService.editBackendSubject(subjectDto, bindingResult, false);
            }
        }
    }

    @GetMapping("/create.do")
    public String createGet(Model model) {
        SubjectDto subjectDto = new SubjectDto();
        model.addAttribute("subject", subjectDto);
        List<DepartmentDto> departmentList = backendDepartmentService.getList();
        model.addAttribute("departments", departmentList);
        return "subjects/create";
    }

    @PostMapping("/create.do")
    public String createPost(@ModelAttribute("subject") @Valid SubjectDto subjectDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "subjects/create";
        } else {
            backendSubjectService.saveBackendSubject(subjectDto);
        }
        return "redirect:/subjects/list.do";
    }

    @RequestMapping(value = "/subject-list.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<SubjectDto> getSubjectList(@RequestBody FilterForm filter) {
        return backendSubjectService.getSubjectDataTables(filter);
    }

    @RequestMapping(value = "/change-department.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    OkResponseForm changeDepartment(@RequestBody JsonNode jsonNode) {
        Long entityId = jsonNode.get("entityId").asLong();
        System.out.println(entityId + " json");
        backendSubjectService.changeSubjectDepartment(entityId);
        return new OkResponseForm();
    }


}
