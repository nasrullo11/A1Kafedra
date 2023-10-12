package uz.istart.kafedra.admin.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.istart.kafedra.admin.form.OkResponseForm;
import uz.istart.kafedra.admin.form.tables.DataTablesForm;
import uz.istart.kafedra.admin.form.tables.FilterForm;
import uz.istart.kafedra.admin.services.BackendFieldService;
import uz.istart.kafedra.core.dtos.*;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/fields")
@RequiredArgsConstructor
public class AdminFieldController {

    private final BackendFieldService backendFieldService;

    @GetMapping("/list.do")
    public String list() {
        return "fields/list";
    }

    @GetMapping("/view.do/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        FieldDto fieldDto = backendFieldService.findById(id);
        model.addAttribute("field", fieldDto);
        return "fields/view";
    }

    @GetMapping("/create.do")
    public String createGet(Model model) {
        FieldDto fieldDto = new FieldDto();
        model.addAttribute("field", fieldDto);
        return "fields/create";
    }

    @PostMapping("/create.do")
    public String createPost(@ModelAttribute("field") @Valid FieldDto fieldDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Xatolik: " + bindingResult.getAllErrors());
            return "fields/create";
        } else {
            backendFieldService.saveBackendField(fieldDto);
        }
        return "redirect:/fields/list.do";
    }

    @GetMapping("/edit.do/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        FieldDto fieldDto = backendFieldService.findById(id);
        model.addAttribute("field", fieldDto);
        return "fields/edit";
    }

    @PostMapping("/edit.do")
    public String editPost(@ModelAttribute("field") @Valid FieldDto fieldDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fields/edit";
        } else {
            if (StringUtils.isEmpty(fieldDto.getId())) {
                throw new TableEntityNotFoundException("Subject not found exception");
            } else {
                return backendFieldService.editBackendField(fieldDto, bindingResult, false);
            }
        }
    }

    @RequestMapping(value = "/delete.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    OkResponseForm deleteField(@RequestBody JsonNode jsonNode) {
        Long entityId = jsonNode.get("entityId").asLong();
        backendFieldService.deleteField(entityId);
        return new OkResponseForm();
    }

    @RequestMapping(value = "field-list.json", method = RequestMethod.POST, headers = {"Accept=application/json", "Content-type=application/json"})
    public @ResponseBody
    DataTablesForm<FieldDto> getFieldList(@RequestBody FilterForm filter) {
        return backendFieldService.getFieldDataTables(filter);
    }


}
