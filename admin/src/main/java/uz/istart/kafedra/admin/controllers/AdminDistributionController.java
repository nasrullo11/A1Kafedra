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
import uz.istart.kafedra.admin.services.*;
import uz.istart.kafedra.core.dtos.*;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/distributions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'Kafedra_mudiri', 'Uqituvchi')")
public class AdminDistributionController {

    private final BackendDistributionService backendDistributionService;
    private final BackendSubjectService backendSubjectService;
    private final BackendUserService backendUserService;
    private final BackendLessonTypeService backendLessonTypeService;
    private final BackendGroupService backendGroupService;

    @GetMapping("/list.do")
    public String list(Model model) {
        List<DistributionDto> disList = backendDistributionService.getListByDep();
        Integer total = 0;
        for(DistributionDto dis : disList){
            total += dis.getSubjectHour();
        }
        model.addAttribute("total", total);
        return "distributions/list";
    }

    @GetMapping("/view.do/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        DistributionDto distributionDto = backendDistributionService.findById(id);
        model.addAttribute("distribution", distributionDto);
        return "distributions/view";
    }

    @GetMapping("/edit.do/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        DistributionDto distributionDto = backendDistributionService.findById(id);
        model.addAttribute("distribution", distributionDto);
        UserDto user = backendUserService.findByAuth();
        List<SubjectDto> subjectList = backendSubjectService.getListByDepartment(user.getDepartment().getId());
        model.addAttribute("subjects", subjectList);
        List<UserDto> userList = backendUserService.getListByDepartment(user.getDepartment().getId());
        model.addAttribute("teachers", userList);
        List<GroupDto> groupList = backendGroupService.getList();
        model.addAttribute("groups", groupList);
        List<LessonTypeDto> lTypeList = backendLessonTypeService.getList();
        model.addAttribute("ltypes", lTypeList);
        return "distributions/edit";
    }

    @PostMapping("/edit.do")
    public String editPost(@ModelAttribute("distribution") @Valid DistributionDto distributionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "distributions/edit";
        } else {
            if (StringUtils.isEmpty(distributionDto.getId())) {
                throw new TableEntityNotFoundException("Distribution not found exception");
            } else {
                return backendDistributionService.editBackendDistribution(distributionDto, bindingResult, false);
            }
        }
    }

    @GetMapping("/create.do")
    public String createGet(Model model) {
        DistributionDto distributionDto = new DistributionDto();
        model.addAttribute("distribution", distributionDto);
        UserDto user = backendUserService.findByAuth();
        List<SubjectDto> subjectList = backendSubjectService.getListByDepartment(user.getDepartment().getId());
        model.addAttribute("subjects", subjectList);
        List<UserDto> userList = backendUserService.getListByDepartment(user.getDepartment().getId());
        model.addAttribute("teachers", userList);
        List<GroupDto> groupList = backendGroupService.getList();
        model.addAttribute("groups", groupList);
        List<LessonTypeDto> lTypeList = backendLessonTypeService.getList();
        model.addAttribute("ltypes", lTypeList);
        return "distributions/create";
    }

    @PostMapping("/create.do")
    public String createPost(@ModelAttribute("distribution") @Valid DistributionDto distributionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "distributions/create";
        } else {
            backendDistributionService.saveBackendDistribution(distributionDto);
        }
        return "redirect:/distributions/list.do";
    }

    @RequestMapping(value = "/distribution-list.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<DistributionDto> getDistributionList(@RequestBody FilterForm filter) {
        return backendDistributionService.getDistributionsByDepartment(filter);
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    OkResponseForm deleteDistribution(@RequestBody JsonNode jsonNode) {
        Long entityId = jsonNode.get("entityId").asLong();
        backendDistributionService.deleteDistribution(entityId);
        return new OkResponseForm();
    }
}
