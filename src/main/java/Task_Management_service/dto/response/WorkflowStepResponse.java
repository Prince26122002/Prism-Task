package Task_Management_service.dto.response;

import Task_Management_service.constant.WorkflowStepType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkflowStepResponse {
    Long stepId;
    Long workflowId;
    WorkflowStepType type;
    int position;
}
