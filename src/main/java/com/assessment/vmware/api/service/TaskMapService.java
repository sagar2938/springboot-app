package com.assessment.vmware.api.service;

import com.assessment.vmware.domain.dto.TaskDTO;
import com.assessment.vmware.domain.dto.TaskResponseDTO;

import java.util.List;
import java.util.Map;

public interface TaskMapService {

    Map createTask(TaskDTO taskDTO);
    Map getTask(String taskId);

    Map getTaskStatus(String taskId);

    Map  generateBulkTasks(List<TaskDTO> taskDTOList);
}
