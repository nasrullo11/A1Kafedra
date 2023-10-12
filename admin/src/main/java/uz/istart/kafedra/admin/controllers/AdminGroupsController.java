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
import uz.istart.kafedra.admin.services.BackendEducationTypeService;
import uz.istart.kafedra.admin.services.BackendFieldService;
import uz.istart.kafedra.admin.services.BackendGroupService;
import uz.istart.kafedra.core.dtos.EducationTypeDto;
import uz.istart.kafedra.core.dtos.FieldDto;
import uz.istart.kafedra.core.dtos.GroupDto;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'Kafedra_mudiri', 'Uqituvchi')")
public class AdminGroupsController {

    private final BackendGroupService backendGroupService;
    private final BackendFieldService backendFieldService;
    private final BackendEducationTypeService backendEducationTypeService;

    @GetMapping("/list.do")
    public String list() {
        return "groups/list";
    }

    @GetMapping("/view.do/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        GroupDto groupDto = backendGroupService.findById(id);
        model.addAttribute("group", groupDto);
        return "groups/view";
    }

    @GetMapping("/edit.do/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        GroupDto groupDto = backendGroupService.findById(id);
        model.addAttribute("group", groupDto);
        List<FieldDto> fieldList = backendFieldService.getList();
        model.addAttribute("fields", fieldList);
        List<EducationTypeDto> eTypeList = backendEducationTypeService.getList();
        model.addAttribute("etypes", eTypeList);
        return "groups/edit";
    }

    @PostMapping("/edit.do")
    public String editPost(@ModelAttribute("group") @Valid GroupDto groupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "groups/edit";
        } else {
            if (StringUtils.isEmpty(groupDto.getId())) {
                throw new TableEntityNotFoundException("Group not found exception");
            } else {
                return backendGroupService.editBackendGroup(groupDto, bindingResult, false);
            }
        }
    }

    @GetMapping("/create.do")
    public String createGet(Model model) {
        GroupDto groupDto = new GroupDto();
        model.addAttribute("group", groupDto);
        List<FieldDto> fieldList = backendFieldService.getList();
        model.addAttribute("fields", fieldList);
        List<EducationTypeDto> eTypeList = backendEducationTypeService.getList();
        model.addAttribute("etypes", eTypeList);
        return "groups/create";
    }

    @PostMapping("/create.do")
    public String createPost(@ModelAttribute("group") @Valid GroupDto groupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Xatolik: " + bindingResult.getAllErrors());
            return "groups/create";
        } else {
            backendGroupService.saveBackendGroup(groupDto);
        }
        return "redirect:/groups/list.do";
    }

    @RequestMapping(value = "/group-list.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<GroupDto> getGroupList(@RequestBody FilterForm filter) {
        return backendGroupService.getGroupDataTables(filter);
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    OkResponseForm deleteGroup(@RequestBody JsonNode jsonNode) {
        Long entityId = jsonNode.get("entityId").asLong();
        backendGroupService.deleteGroup(entityId);
        return new OkResponseForm();
    }
}
