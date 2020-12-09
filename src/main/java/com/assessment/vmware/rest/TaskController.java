package com.assessment.vmware.rest;

import com.assessment.vmware.api.service.TaskMapService;
import com.assessment.vmware.common.Constants;
import com.assessment.vmware.domain.dto.TaskDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TaskController extends BaseController{

    @Autowired
    TaskMapService taskMapService;


    @RequestMapping(value = "/tasks/{taskId}", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    @ApiOperation(value = "Get the task details based on the taskId(uuid)", notes = "Get the task details based on the taskId(uuid).", response=Map.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "Task"),
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "TASK NOT FOUND"),
        @ApiResponse(code = 500, message = "Bad Request")})
    public ResponseEntity<Map> getTasks(@ApiParam(value = "taskId", required = true) @PathVariable("taskId") String taskId,
                                        @ApiParam(value = "action", required = true) @RequestParam("action") String action){

        Map taskResponse = null;
        try
        {
            if (Constants.ACTION_GET_NUM_LIST.equals(action))
            {
                 taskResponse = taskMapService.getTask(taskId);
                 if(taskResponse == null){
                     ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
                     return responseEntity;
                 }
            }else{
                errorMessages.add("Invalid action");
            }
        }catch (Exception e){
            errorMessages.add(e.getMessage());
        }

        return getApiResponse(taskResponse);
    }


    @RequestMapping(value = "/tasks/{taskId}/status", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
    @ApiOperation(value = "Get the task status based on the taskId(uuid)", notes = "Get the task status based on the taskId(uuid).", response=Map.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "Task"),
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "TASK NOT FOUND"),
        @ApiResponse(code = 500, message = "Bad Request")})
    public ResponseEntity<Map> getTaskStatus(@ApiParam(value = "taskId", required = true) @PathVariable("taskId")String taskId){
        Map taskStatusResponse = null;
        try
        {
             taskStatusResponse = taskMapService.getTaskStatus(taskId);
            if (taskStatusResponse == null)
            {
                ResponseEntity<Map> taskStatusResp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                return taskStatusResp;
            }

        }catch (Exception e){
            errorMessages.add(e.getMessage());
        }
        return getApiResponse(taskStatusResponse);
    }

    @RequestMapping(value = "/generate", produces = { MediaType.APPLICATION_JSON_VALUE },consumes = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new task", notes = "Creates a new task.", response=Map.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "Task"),
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Bad Request")})
    public ResponseEntity<Map> generateTask(@RequestBody TaskDTO taskDTO){
        Map taskResponse = new HashMap();
        try
        {
             taskResponse = taskMapService.createTask(taskDTO);
        }catch (Exception e){
            errorMessages.add(e.getMessage());
        }
        return getApiResponse(taskResponse);
    }

    @RequestMapping(value = "/bulkGenerate", produces = { MediaType.APPLICATION_JSON_VALUE },consumes = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    @ApiOperation(value = "Creates new tasks in bulk", notes = "Creates tasks in bulk", response=Map.class)
    @ApiResponses(value = { @ApiResponse(code = 404, message = "Task"),
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Bad Request")})
    public ResponseEntity<Map> generateBulkTask(@RequestBody List<TaskDTO>taskDTOS){
        Map taskResponse = null;
        try
        {
             taskResponse = taskMapService.generateBulkTasks(taskDTOS);

        }catch (Exception e){
            errorMessages.add(e.getMessage());
        }
        return getApiResponse(taskResponse);
    }
}
