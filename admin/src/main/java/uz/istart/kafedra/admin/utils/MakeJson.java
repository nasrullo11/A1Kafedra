package uz.istart.kafedra.admin.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import uz.istart.kafedra.core.dtos.*;

import java.util.List;

public class MakeJson {
    public static JSONArray subjectDataJson(List<SubjectDto> subjectList) {
        JSONArray subjectJson = new JSONArray();
        for (SubjectDto subjectDto : subjectList) {
            JSONObject data = new JSONObject();
            data.put("id", subjectDto.getId());
            data.put("subjectName", subjectDto.getSubjectName());
            data.put("subjectBall", subjectDto.getSubjectBall());
            data.put("subjectDepartment", subjectDto.getSubjectDepartment().getDepartmentName());
            subjectJson.add(data);
        }
        return subjectJson;
    }

    public static JSONArray groupDataJson(List<GroupDto> groupList) {
        JSONArray groupJson = new JSONArray();
        for (GroupDto groupDto : groupList) {
            JSONObject data = new JSONObject();
            data.put("id", groupDto.getId());
            data.put("groupName", groupDto.getGroupName());
            data.put("entranceYear", groupDto.getEntranceYear());
            data.put("groupField", groupDto.getGroupField().getFieldName());
            data.put("groupEducationType", groupDto.getGroupEducationType().getEducationTypeName());
            data.put("amountStudents", groupDto.getAmountStudents());
            groupJson.add(data);
        }
        return groupJson;
    }

    public static JSONArray loadBySubjectsDataJson(List<LoadBySubjectsDto> listL, List<GroupByFieldsDto> listG ) {
        JSONArray loadJson = new JSONArray();
        for(LoadBySubjectsDto load : listL) {
            JSONObject data = new JSONObject();
            data.put("subjectId", load.getSubjectId());
            data.put("subjectName", load.getSubjectName());

            data.put("totalHour", load.getTotalHour());
            loadJson.add(load);
        }
        return loadJson;
    }

    public static JSONArray workDataJson(List<WorkDto> workList) {
        JSONArray workJson = new JSONArray();
        for (WorkDto workDto : workList) {
            JSONObject data = new JSONObject();
            data.put("id", workDto.getId());
            data.put("workTitle", workDto.getWorkTitle());
            data.put("workTeacher", workDto.getWorkTeacher().getFullName());
            data.put("workType", workDto.getWorkType().getWorkTypeName());
            data.put("workDeadline", workDto.getWorkDeadline());
            data.put("workStatus", workDto.getWorkStatus().getWorkStatusName());
            data.put("workDescription", workDto.getWorkDescription());
            workJson.add(data);
        }
        return workJson;
    }

    public static JSONArray usernameDataJson(List<UserDto> userList) {
        JSONArray userJson = new JSONArray();
        for(UserDto userDto : userList) {
            JSONObject data = new JSONObject();
            data.put("username", userDto.getUsername());
            userJson.add(data);
        }
        return userJson;
    }
}

