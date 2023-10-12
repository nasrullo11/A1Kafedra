package uz.istart.kafedra.admin.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.ObjectNotFoundException;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.istart.kafedra.admin.services.BackendDistributionService;
import uz.istart.kafedra.admin.services.BackendGroupService;
import uz.istart.kafedra.admin.services.BackendSubjectService;
import uz.istart.kafedra.admin.services.BackendUserService;
import uz.istart.kafedra.admin.utils.AjaxResponseBody;
import uz.istart.kafedra.admin.utils.MakeJson;
import uz.istart.kafedra.core.constants.Views;
import uz.istart.kafedra.core.dtos.DistributionByUsersDto;
import uz.istart.kafedra.core.dtos.GroupDto;
import uz.istart.kafedra.core.dtos.SubjectDto;
import uz.istart.kafedra.core.dtos.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ajax")
public class AdminAjaxController {

    private final BackendUserService backendUserService;
    private final BackendDistributionService backendDistributionService;
    private final BackendSubjectService backendSubjectService;
    private final BackendGroupService backendGroupService;

    @RequestMapping(value = {"/m_subject_list"})
    public void subjectList(HttpServletRequest request,
                            HttpServletResponse response, Model model) throws IOException, ObjectNotFoundException {
        JSONObject stringJson = new JSONObject();
        List<SubjectDto> subjectDtoList = backendSubjectService.getList();
        JSONArray list = MakeJson.subjectDataJson(subjectDtoList);
        stringJson.put("subjectList", list);
        String json = new Gson().toJson(stringJson);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/search/api/getSearchResult")
    public AjaxResponseBody getSearchResultViaAjax(@RequestBody Long userId) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (isValidSearchCriteria(userId)) {
            UserDto user = backendUserService.findById(userId);
                result.setCode("200");
                result.setMsg("");
                result.setResult(user);
                result.setUsername(user.getUsername());
        } else {
            result.setCode("400");
            result.setMsg("Search criteria is empty!");
        }

        //AjaxResponseBody will be converted into json format and send back to client.
        return result;

    }

    private boolean isValidSearchCriteria(Long search) {

        boolean valid = true;

        if (search == null) {
            valid = false;
        }

        if ((StringUtils.isEmpty(search))) {
            valid = false;
        }

        return valid;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/totalHourByUserId")
    public AjaxResponseBody getTotalHourViaAjax(@RequestBody Long userId) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (isValidSearchCriteria(userId)) {
            try {
                DistributionByUsersDto user = backendDistributionService.getDistributionByUser(userId);
                result.setCode("200");
                result.setMsg("");
                result.setDisResult(user);
                result.setUsername(user.getUsername());
                result.setTotalHour(user.getTotalHour());
            } catch (Exception e) {
                result.setCode("400");
                result.setMsg("Null");
                result.setUsername("null");
            }
        } else {
            result.setCode("400");
            result.setMsg("Search criteria is empty!");
        }

        //AjaxResponseBody will be converted into json format and send back to client.
        return result;

    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/controlHourByGroupId")
    public AjaxResponseBody getControlHourViaAjax(@RequestBody Long groupId) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (isValidSearchCriteria(groupId)) {
            try {
                GroupDto group = backendGroupService.findById(groupId);
                result.setCode("200");
                result.setMsg("");
                result.setTotalHour(group.getAmountStudents()/2);
            } catch (Exception e) {
                result.setCode("400");
                result.setMsg("Null");
                result.setUsername("null");
            }
        } else {
            result.setCode("400");
            result.setMsg("Search criteria is empty!");
        }

        //AjaxResponseBody will be converted into json format and send back to client.
        return result;

    }
}
