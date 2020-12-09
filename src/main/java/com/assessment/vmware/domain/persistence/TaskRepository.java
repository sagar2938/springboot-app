package com.assessment.vmware.domain.persistence;

import com.assessment.vmware.domain.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {
}
