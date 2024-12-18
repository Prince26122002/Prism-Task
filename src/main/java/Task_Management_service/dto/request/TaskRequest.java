package Task_Management_service.dto.request;

import Task_Management_service.constant.TaskPriority;
import Task_Management_service.constant.TaskStatus;
import Task_Management_service.constant.TaskType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequest {
    String title;
    String description;
    TaskStatus status;
    TaskPriority priority;
    TaskType type;
    Date dueDate;
    Long assigneeId;
    Long projectId;
    Long reporterId;
}
