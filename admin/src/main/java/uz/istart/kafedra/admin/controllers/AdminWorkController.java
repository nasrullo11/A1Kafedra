package uz.istart.kafedra.admin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.istart.kafedra.admin.form.OkResponseForm;
import uz.istart.kafedra.admin.form.WorkStatusForm;
import uz.istart.kafedra.admin.form.tables.DataTablesForm;
import uz.istart.kafedra.admin.form.tables.FilterForm;
import uz.istart.kafedra.admin.services.BackendUserService;
import uz.istart.kafedra.admin.services.BackendWorkService;
import uz.istart.kafedra.admin.services.BackendWorkTypeService;
import uz.istart.kafedra.core.dtos.WorkDto;
import uz.istart.kafedra.core.dtos.WorkTypeDto;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/works")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'Kafedra_mudiri', 'Uqituvchi')")
public class AdminWorkController {
    private final BackendWorkService backendWorkService;
    private final BackendWorkTypeService backendWorkTypeService;
    private final BackendUserService backendUserService;

    @GetMapping("/listType1.do")
    public String listType1() {
        return "works/listType1";
    }

    @GetMapping("/listType2.do")
    public String listType2() {
        return "works/listType2";
    }

    @GetMapping("/listType3.do")
    public String listType3() {
        return "works/listType3";
    }

    @GetMapping("/listType4.do")
    public String listType4() {
        return "works/listType4";
    }

    @GetMapping("/view.do/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        WorkDto workDto = backendWorkService.findById(id);
        model.addAttribute("work", workDto);
        return "works/view";
    }

    @GetMapping("/edit.do/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        WorkDto workDto = backendWorkService.findById(id);
        model.addAttribute("work", workDto);
        return "works/edit";
    }

    @PostMapping("/edit.do")
    public String editPost(@ModelAttribute("work") @Valid WorkDto workDto, BindingResult bindingResult) {
        System.out.println(workDto.getWorkType().getWorkTypeName() + " sdfsdf");
        if (bindingResult.hasErrors()) {
            return "works/edit";
        } else {
            if (StringUtils.isEmpty(workDto.getId())) {
                throw new TableEntityNotFoundException("Work not found exception");
            } else {
                return backendWorkService.editBackendWork(workDto, bindingResult, false);
            }
        }
    }

    @GetMapping("/create.do/{from}")
    public String createGet(@PathVariable(value = "from", required = false) Integer from, Model model) {
        WorkDto workDto = new WorkDto();
        if(from != null) {
            WorkTypeDto workTypeDto = backendWorkTypeService.findById(from.longValue());
            workDto.setWorkType(workTypeDto);
        }
        model.addAttribute("work", workDto);
        List<WorkTypeDto> workTypeDtoList = backendWorkTypeService.getList();
        return "works/create";
    }

    @PostMapping("/create.do")
    public String createPost(@ModelAttribute("work") @Valid WorkDto workDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "works/create";
        } else {
            backendWorkService.saveBackendWork(workDto);
        }
        if(workDto.getWorkType().getId() == 1L){
            return "redirect:/works/listType1.do";
        }
        else if(workDto.getWorkType().getId() == 2L){
            return "redirect:/works/listType2.do";
        }
        else if(workDto.getWorkType().getId() == 3L){
            return "redirect:/works/listType3.do";
        }
        else{
            return "redirect:/works/listType4.do";
        }
    }

    @RequestMapping(value = "/work-list-type-id1.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<WorkDto> getWorkListType1(@RequestBody FilterForm filter) {
        return backendWorkService.getWorkDataTablesByTypeId1(filter);
    }

    @RequestMapping(value = "/work-list-type-id2.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<WorkDto> getWorkListType2(@RequestBody FilterForm filter) {
        return backendWorkService.getWorkDataTablesByTypeId2(filter);
    }

    @RequestMapping(value = "/work-list-type-id3.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<WorkDto> getWorkListType3(@RequestBody FilterForm filter) {
        return backendWorkService.getWorkDataTablesByTypeId3(filter);
    }

    @RequestMapping(value = "/work-list-type-id4.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<WorkDto> getWorkListType4(@RequestBody FilterForm filter) {
        return backendWorkService.getWorkDataTablesByTypeId4(filter);
    }

    @RequestMapping(value = "/status.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    OkResponseForm changeWorkStatus(@RequestBody WorkStatusForm statusForm) {
        System.out.println(1 + "   " + statusForm.getStatusId() + " ----- " + statusForm.getEntityId() + " ----- " + statusForm.isEnable());
        backendWorkService.changeWorkStatus(statusForm);
        return new OkResponseForm();
    }
}
