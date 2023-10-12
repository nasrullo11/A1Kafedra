package uz.istart.kafedra.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.istart.kafedra.api.services.ApiWorksRestService;
import uz.istart.kafedra.core.dtos.WorkDto;
import uz.istart.kafedra.core.entities.WorkEntity;
import uz.istart.kafedra.core.exceptions.entity.TableEntityNotFoundException;
import uz.istart.kafedra.core.repositories.WorkRepository;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/works")
@RequiredArgsConstructor
public class ApiWorksController {

    private final ApiWorksRestService apiWorksRestService;
    private final WorkRepository workRepository;

    @GetMapping(value = "/listByWorkType")
    @ResponseBody
    public List<WorkDto> getListWorkType(Long workTypeId, Long userId) {
        List<WorkDto> wd = workRepository.findByWorkTypeIdAndTeacherId(workTypeId, userId)
                .stream()
                .map(WorkEntity::getDto)
                .collect(Collectors.toList());
        Collections.sort(wd, new Comparator<WorkDto>() {
            @Override
            public int compare(WorkDto workDto, WorkDto t1) {
                return t1.getId().compareTo(workDto.getId());
            }
        });
        return wd;
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public HashMap<String, Boolean> create(Long userId, String workTitle, Long workTypeId, String workDeadline, String workDescription){
//        System.out.println(userId + "     " + workTypeId + "  " + workDescription + "   8888");
        HashMap<String, Boolean> hm = new HashMap<>();
        try{
            apiWorksRestService.saveWork(userId, workTitle, workTypeId, workDeadline, workDescription);
            hm.put("status", true);
        } catch (Exception e) {
            hm.put("status", false);
        }
        return hm;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public HashMap<String, Boolean> edit(Long workId, String workTitle, String workDeadline, String workDescription){
        //System.out.println(workId + "   " + workTitle + "   " + workDeadline + "   " + workDescription);
        WorkEntity workEntity = new WorkEntity();
        HashMap<String, Boolean> hm = new HashMap<>();
        try{
            Optional<WorkEntity> workEntityOptional = workRepository.findById(workId);
            if (workEntityOptional.isPresent()) {
                workEntity = workEntityOptional.get();
            } else {
                throw new TableEntityNotFoundException("Work not found exception!");
            }
            apiWorksRestService.editWork(workEntity, workTitle, workDeadline, workDescription);
            hm.put("status", true);
        } catch (Exception e) {
            hm.put("status", false);
        }
        return hm;
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    public HashMap<String, Boolean> delete(Long workId){
        WorkEntity workEntity = new WorkEntity();
        HashMap<String, Boolean> hm = new HashMap<>();
        try{
            Optional<WorkEntity> workEntityOptional = workRepository.findById(workId);
            if (workEntityOptional.isPresent()) {
                workEntity = workEntityOptional.get();

            } else {
                throw new TableEntityNotFoundException("Work not found exception!");
            }
            apiWorksRestService.deleteWork(workEntity);
            hm.put("status", true);
        } catch (Exception e) {
            hm.put("status", false);
        }
        return hm;
    }

    @PostMapping(value = "/change-status")
    @ResponseBody
    public HashMap<String, Boolean> changeStatus(Long workId, Long statusId){
        WorkEntity workEntity = new WorkEntity();
        HashMap<String, Boolean> hm = new HashMap<>();
        try{
            Optional<WorkEntity> workEntityOptional = workRepository.findById(workId);
            if (workEntityOptional.isPresent()) {
                workEntity = workEntityOptional.get();

            } else {
                throw new TableEntityNotFoundException("Work not found exception!");
            }
            apiWorksRestService.changeStatus(workEntity, statusId);
            hm.put("status", true);
        } catch (Exception e) {
            hm.put("status", false);
        }
        return hm;
    }

}
