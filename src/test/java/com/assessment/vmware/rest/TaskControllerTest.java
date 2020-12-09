package com.assessment.vmware.rest;

import com.assessment.vmware.common.Constants;
import com.assessment.vmware.domain.dto.TaskDTO;
import com.assessment.vmware.domain.dto.TaskResponseDTO;
import com.assessment.vmware.domain.enums.TaskSatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.*;

@SpringBootTest
public class TaskControllerTest
{
    @Autowired
    TaskController taskController;

    /**
     * test the task generation, status check apis for single task
     * @throws InterruptedException
     */
    @Test
    public void generateTaskTest()
        throws InterruptedException
    {
        TaskDTO taskDTO = TaskDTO.builder().goal(10).step(2).build();
       ResponseEntity<Map> task = taskController.generateTask(taskDTO);
        Assertions.assertNotNull(task.getBody().get(Constants.TASK_KEY));
        ResponseEntity<Map> taskStatus = taskController.getTaskStatus((String) task.getBody().get(Constants.TASK_KEY));
        Assertions.assertEquals(taskStatus.getBody().get(Constants.RESULT_KEY), TaskSatus.IN_PROGRESS.getStatus());
        Thread.sleep(1000);
        String expectedSequence = "10,8,6,4,2,0";
        ResponseEntity<Map> taskCreated = taskController.getTasks((String) task.getBody().get(Constants.TASK_KEY), Constants.ACTION_GET_NUM_LIST);
        Assertions.assertEquals(taskCreated.getBody().get(Constants.RESULT_KEY),expectedSequence);

        taskStatus = taskController.getTaskStatus((String) task.getBody().get(Constants.TASK_KEY));
        Assertions.assertEquals(taskStatus.getBody().get(Constants.RESULT_KEY), TaskSatus.SUCCESS.getStatus());


    }


    /**
     * test the task generation, status check apis for bulk tasks
     * @throws InterruptedException
     */
    @Test
    public void generateBulkTaskTest()
        throws InterruptedException
    {
        TaskDTO taskDTO = TaskDTO.builder().goal(20).step(2).build();
        TaskDTO taskDTO2 = TaskDTO.builder().goal(10).step(2).build();
        ResponseEntity<Map> bulkTask = taskController.generateBulkTask(Arrays.asList(taskDTO,taskDTO2));
        Assertions.assertNotNull(bulkTask.getBody().get(Constants.TASK_KEY));
        ResponseEntity<Map> bulkTaskStatus = taskController.getTaskStatus((String)bulkTask.getBody().get(Constants.TASK_KEY));
        Assertions.assertEquals(bulkTaskStatus.getBody().get(Constants.RESULT_KEY), TaskSatus.IN_PROGRESS.getStatus());
        Thread.sleep(1000);

         bulkTaskStatus = taskController.getTaskStatus((String)bulkTask.getBody().get(Constants.TASK_KEY));
        Assertions.assertEquals(bulkTaskStatus.getBody().get(Constants.RESULT_KEY), TaskSatus.SUCCESS.getStatus());

        List<String> expectedSequences = Arrays.asList("20,18,16,14,12,10,8,6,4,2,0", "10,8,6,4,2,0");
        ResponseEntity<Map> taskCreated2 = taskController.getTasks((String) bulkTask.getBody().get(Constants.TASK_KEY), Constants.ACTION_GET_NUM_LIST);
        List<String> actualSequences = (List<String>) taskCreated2.getBody().get(Constants.RESULT_KEY);
        Assertions.assertEquals(actualSequences.size(), 2);
        for (int i = 0; i < actualSequences.size(); i++)
        {
            String taskHeader = actualSequences.get(i);
            Assertions.assertEquals(taskHeader, expectedSequences.get(i));
        }

        bulkTaskStatus = taskController.getTaskStatus((String) bulkTask.getBody().get(Constants.TASK_KEY));
        Assertions.assertEquals(bulkTaskStatus.getBody().get(Constants.RESULT_KEY), TaskSatus.SUCCESS.getStatus());
    }
    }
