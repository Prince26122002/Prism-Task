package Task_Management_service.repository;

import Task_Management_service.entity.WorkflowStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowStepRepository extends JpaRepository<WorkflowStepEntity, Long> {

    List<WorkflowStepEntity> findByWorkflowIdOrderByPositionAsc(Long workflowId);
}
