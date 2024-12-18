package Task_Management_service.dto.request;

import Task_Management_service.constant.WorkflowStepType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkflowStepRequest {
    Long workflowId;
    WorkflowStepType type;
    int position;
}
