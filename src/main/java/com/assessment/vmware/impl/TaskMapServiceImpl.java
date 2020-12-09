package com.assessment.vmware.impl;

import com.assessment.vmware.api.service.TaskMapService;
import com.assessment.vmware.api.service.TaskService;
import com.assessment.vmware.common.Constants;
import com.assessment.vmware.domain.dto.TaskDTO;
import com.assessment.vmware.domain.dto.TaskResponseDTO;
import com.assessment.vmware.domain.entity.SequenceEntity;
import com.assessment.vmware.domain.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TaskMapServiceImpl implements TaskMapService {

    @Autowired
    TaskService taskService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Map createTask(TaskDTO taskDTO) {

        TaskEntity task = taskService.generateTask(taskDTO);
        Map<String,Object> response = new HashMap<>();
        response.put(Constants.TASK_KEY, task.getTaskId() );
        return  response;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public Map getTask(String taskId) {
        TaskEntity task = taskService.getTask(taskId);
        if(task == null)return null;
        Map<String,Object> response = new HashMap<>();
        List<String> results = task.getSequences().stream().map(SequenceEntity::getSequence).collect(Collectors.toList());
        response.put(Constants.RESULT_KEY,results.size()==1?results.get(0):results);
        return response;

    }
    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public Map getTaskStatus(String taskId){
        TaskEntity task = taskService.getTask(taskId);
        Map<String,Object> taskStatusMap = new HashMap<>();
        taskStatusMap.put("result",task.getStatus());
        return taskStatusMap;
    }

    @Override
    public Map generateBulkTasks(List<TaskDTO> taskDTOList)
    {
       TaskEntity taskEntity = taskService.generateBulkTask(taskDTOList);

      Map<String,Object> response = new HashMap<>();
      response.put(Constants.TASK_KEY,taskEntity.getTaskId());
        return  response;
    }
}
