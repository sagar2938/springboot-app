package com.assessment.vmware.api.service;

import com.assessment.vmware.domain.dto.TaskDTO;
import com.assessment.vmware.domain.entity.TaskEntity;

import java.util.List;
import java.util.Map;

public interface TaskService {

     TaskEntity createEmptyTask();

     TaskEntity getTask(String taskId);

     TaskEntity generateTask(TaskDTO taskDTO);

    TaskEntity generateBulkTask(List<TaskDTO> taskDTOS);
}
