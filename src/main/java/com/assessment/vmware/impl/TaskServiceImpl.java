package com.assessment.vmware.impl;


import com.assessment.vmware.api.service.TaskService;
import com.assessment.vmware.domain.dto.TaskDTO;
import com.assessment.vmware.domain.entity.SequenceEntity;
import com.assessment.vmware.domain.entity.TaskEntity;
import com.assessment.vmware.domain.enums.TaskSatus;
import com.assessment.vmware.domain.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public TaskEntity createEmptyTask() {
        String uuid = UUID.randomUUID().toString();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskId(uuid);
        taskEntity.setStatus(TaskSatus.IN_PROGRESS.getStatus());;
        taskEntity.setSequences(new ArrayList<>());
        TaskEntity taskEntityCreated = taskRepository.save(taskEntity);
        return taskEntityCreated;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void startTaskProgress(List<TaskDTO> tasks, String taskId){
        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);
        if(taskEntity.isPresent()){
            TaskEntity taskEntityCreated = taskEntity.get();
            try
            {

                taskEntityCreated.setStatus(TaskSatus.SUCCESS.getStatus());
                List<SequenceEntity> sequenceEntities =tasks.stream().map(taskDTO -> generateNumbers(taskDTO.getGoal(),taskDTO.getStep())).collect(Collectors.toList());
                taskEntityCreated.getSequences().addAll(sequenceEntities);
                taskEntityCreated.setStatus(TaskSatus.SUCCESS.getStatus());
                taskEntityCreated =taskRepository.save(taskEntityCreated);
            }catch (Exception e){
                taskEntityCreated.setStatus(TaskSatus.ERROR.getStatus());
                taskEntityCreated =taskRepository.save(taskEntityCreated);
            }
        }
    }
    @Override
    public TaskEntity generateTask(TaskDTO taskDTO) {
        Random random = new Random();
        return generateBulkTask(Arrays.asList(taskDTO));

    }

    @Override
    public TaskEntity generateBulkTask(List<TaskDTO> taskDTOS) {
        Random random = new Random();
        TaskEntity taskEntity = createEmptyTask();
        int delay = random.nextInt(5);
        executorService.schedule(()->{startTaskProgress(taskDTOS, taskEntity.getTaskId());}, delay*100, TimeUnit.MILLISECONDS);
        return taskEntity;

    }


    @Override
    public TaskEntity getTask(String taskId) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);
        if(!taskEntity.isPresent()) return null;
        return taskEntity.get();
    }


    private SequenceEntity generateNumbers(int start, int step){
        SequenceEntity sequenceEntity = new SequenceEntity();
        List<String> numbers = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i =start;i>=0;i=i-step){
            numbers.add(i+"");
        }
        sequenceEntity.setSequence(numbers.stream().collect(Collectors.joining(",")));
        String result = stringBuilder.toString();
        return sequenceEntity;
    }
}
