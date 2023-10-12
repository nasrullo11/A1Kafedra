package uz.istart.kafedra.admin.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.istart.kafedra.admin.services.*;
import uz.istart.kafedra.admin.utils.MakeJson;
import uz.istart.kafedra.core.dtos.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMINISTRATOR','MANAGER', 'Kafedra_mudiri', 'Uqituvchi')")
public class AdminMainController {

    private final BackendSubjectService backendSubjectService;
    private final BackendWorkService backendWorkService;
    private final BackendGroupService backendGroupService;
    private final BackendLoadService backendLoadService;
    private final BackendUserService backendUserService;

    @GetMapping("/main.do")
    public String main(Model model){
        return "dashboard/main";
    }

    @RequestMapping(params = {"action=m_subject_list"})
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

    @RequestMapping(params = {"action=m_work_list_by_type"})
    public void workListByType(HttpServletRequest request,
                            HttpServletResponse response, Model model,
                            @RequestParam(value = "workTypeId") Long workTypeId
    ) throws IOException {
        JSONObject stringJson = new JSONObject();
        List<WorkDto> workList = backendWorkService.getListByType(workTypeId);
        JSONArray list = MakeJson.workDataJson(workList);
        stringJson.put("workListByType", list);
        String json = new Gson().toJson(stringJson);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @RequestMapping(params = {"action=m_group_list"})
    public void groupList(HttpServletRequest request,
                            HttpServletResponse response, Model model) throws IOException, ObjectNotFoundException {
        JSONObject stringJson = new JSONObject();
        List<GroupDto> groupDtoList = backendGroupService.getList();
        JSONArray list = MakeJson.groupDataJson(groupDtoList);
        stringJson.put("groupList", list);
        String json = new Gson().toJson(stringJson);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @RequestMapping(params = {"action=load_by_subject_list"})
    public void loadBySubjectList(HttpServletRequest request,
                          HttpServletResponse response, Model model) throws IOException, ObjectNotFoundException {
        JSONObject stringJson = new JSONObject();
        List<LoadBySubjectsDto> subjectDtoList = backendLoadService.getLoadTotalHours();
        List<GroupByFieldsDto> groupByFieldsDtoList = backendGroupService.getGroupNumbersByFields();
        JSONArray list = MakeJson.loadBySubjectsDataJson(subjectDtoList, groupByFieldsDtoList);
        stringJson.put("loadBySubject", list);
        String json = new Gson().toJson(stringJson);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @RequestMapping(params = {"action=username_list"})
    public void usernameList(HttpServletRequest request,
                                  HttpServletResponse response, Model model) throws IOException, ObjectNotFoundException {
        JSONObject stringJson = new JSONObject();
        List<UserDto> userList = backendUserService.getList();
        JSONArray list = MakeJson.usernameDataJson(userList);
        stringJson.put("usernameList", list);
        String json = new Gson().toJson(stringJson);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
