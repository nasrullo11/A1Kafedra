package uz.istart.kafedra.admin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.istart.kafedra.admin.form.tables.DataTablesForm;
import uz.istart.kafedra.admin.form.tables.FilterForm;
import uz.istart.kafedra.admin.services.BackendFieldService;
import uz.istart.kafedra.admin.services.BackendLoadService;
import uz.istart.kafedra.admin.services.BackendSubjectService;
import uz.istart.kafedra.admin.services.BackendUserService;
import uz.istart.kafedra.core.dtos.FieldDto;
import uz.istart.kafedra.core.dtos.LoadDto;
import uz.istart.kafedra.core.dtos.SubjectDto;
import uz.istart.kafedra.core.dtos.UserDto;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/loads")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'Kafedra_mudiri', 'Uqituvchi')")
public class AdminLoadController {
    private final BackendLoadService backendLoadService;
    private final BackendFieldService backendFieldService;
    private final BackendSubjectService backendSubjectService;
    private final BackendUserService backendUserService;

    @GetMapping("/list.do/{subjectId}")
    public String list(@PathVariable("subjectId") Long subjectId, Model model) {
        SubjectDto subject = backendSubjectService.findById(subjectId);
        model.addAttribute("subject", subject);
        return "loads/list";
    }

    @RequestMapping(value = "/load-list.json/{subjectId}", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<LoadDto> getLoadList(@PathVariable("subjectId") Long subjectId, @RequestBody FilterForm filter) {
        return backendLoadService.getLoadDataTables(subjectId, filter);
    }

    @GetMapping("/view.do/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        LoadDto loadDto = backendLoadService.findById(id);
        model.addAttribute("load", loadDto);
        return "loads/view";
    }

    @GetMapping("/edit.do/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        LoadDto loadDto = backendLoadService.findById(id);
        model.addAttribute("load", loadDto);
        List<SubjectDto> subjectList = backendSubjectService.getList();
        model.addAttribute("subjects", subjectList);
        List<FieldDto> fieldList = backendFieldService.getList();
        model.addAttribute("fields", fieldList);
        return "loads/edit";
    }

    @PostMapping("/edit.do")
    public String editPost(@ModelAttribute("load") @Valid LoadDto loadDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "loads/edit";
        } else {
            if (StringUtils.isEmpty(loadDto.getId())) {
                throw new TableEntityNotFoundException("Load not found exception");
            } else {
                return backendLoadService.editBackendLoad(loadDto, bindingResult, false);
            }
        }
    }

    @GetMapping("/create.do/{subjectId}")
    public String createGet(@PathVariable(value = "subjectId", required = false) Long subjectId, Model model) {
        SubjectDto subjectDto = backendSubjectService.findById(subjectId);
        LoadDto loadDto = new LoadDto();
        loadDto.setLoadSubject(subjectDto);
        System.out.println(loadDto.getLoadSubject().getSubjectName() + " fan nomi");
        model.addAttribute("load", loadDto);
        UserDto user = backendUserService.findByAuth();
        List<FieldDto> fieldList = backendFieldService.getList();
        model.addAttribute("fields", fieldList);
        return "loads/create";
    }

    @PostMapping("/create.do")
    public String createPost(@ModelAttribute("load") @Valid LoadDto loadDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "loads/create";
        } else {
            backendLoadService.saveBackendLoad(loadDto, false);
        }
        return "redirect:/loads/list.do/" + loadDto.getLoadSubject().getId();
    }



    @GetMapping("/list-by-subjects.do")
    public String listBySubjects() {
        return "loads/listBySubjects";
    }

    @GetMapping("/list-by-subjects/create.do")
    public String createGetListBySubject(Model model) {
        LoadDto loadDto = new LoadDto();
        model.addAttribute("load", loadDto);
        List<SubjectDto> subjectList = backendSubjectService.getList();
        model.addAttribute("subjects", subjectList);
        UserDto user = backendUserService.findByAuth();
        List<FieldDto> fieldList = backendFieldService.getList();
        model.addAttribute("fields", fieldList);
        return "loads/createSubjectLoad";
    }

    @PostMapping("/list-by-subjects/create.do")
    public String createPostListBySubject(@ModelAttribute("load") @Valid LoadDto loadDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "loads/createSubjectLoad";
        } else {
            backendLoadService.saveBackendLoad(loadDto, false);
        }
        return "redirect:/loads/list-by-subjects.do";
    }

}

