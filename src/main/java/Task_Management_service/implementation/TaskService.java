package Task_Management_service.implementation;

import Task_Management_service.constant.ApiErrorCodes;
import Task_Management_service.dto.request.TaskRequest;
import Task_Management_service.dto.request.UserReqDto;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.TaskResponse;
import Task_Management_service.entity.ProjectEntity;
import Task_Management_service.entity.TaskEntity;
import Task_Management_service.entity.UserEntity;
import Task_Management_service.exception.NoSuchElementFoundException;
import Task_Management_service.repository.ProjectRepository;
import Task_Management_service.repository.TaskRepository;
import Task_Management_service.repository.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {


    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepo userRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepo userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse createTask(TaskRequest request) {
        TaskEntity entity = mapDtoToEntity(request);
        TaskEntity savedEntity = taskRepository.save(entity);
        return mapEntityToDto(savedEntity);
    }

    public TaskResponse getTaskById(Long id) {
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.TASK_NOT_FOUND.getErrorCode(), ApiErrorCodes.TASK_NOT_FOUND.getErrorMessage()));
        return mapEntityToDto(entity);
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.TASK_NOT_FOUND.getErrorCode(), ApiErrorCodes.TASK_NOT_FOUND.getErrorMessage()));
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setPriority(request.getPriority());
        entity.setType(request.getType());
        entity.setDueDate(request.getDueDate());
        return mapEntityToDto(taskRepository.save(entity));
    }

    public void deleteTask(Long id) {
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.TASK_NOT_FOUND.getErrorCode(), ApiErrorCodes.TASK_NOT_FOUND.getErrorMessage()));
        taskRepository.delete(entity);
    }

    public PaginatedResp<TaskResponse> getAllTasks(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TaskEntity> taskPage = taskRepository.findAll(pageable);

        List<TaskResponse> responseList = taskPage.getContent()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                taskPage.getTotalElements(),
                taskPage.getTotalPages(),
                page,
                responseList
        );
    }

    private TaskEntity mapDtoToEntity(TaskRequest request) {
        ProjectEntity project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PROJECT_NOT_FOUND.getErrorCode(), ApiErrorCodes.PROJECT_NOT_FOUND.getErrorMessage()));

        UserEntity assignee = null;
        if (request.getAssigneeId() != null) {
            assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.ASSIGNEE_NOT_FOUND.getErrorCode(), ApiErrorCodes.ASSIGNEE_NOT_FOUND.getErrorMessage()));
        }
        UserEntity reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.REPORTER_NOT_FOUND.getErrorCode(), ApiErrorCodes.REPORTER_NOT_FOUND.getErrorMessage()));
        TaskEntity entity = new TaskEntity();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setPriority(request.getPriority());
        entity.setType(request.getType());
        entity.setDueDate(request.getDueDate());
        entity.setProject(project);
        entity.setAssignee(assignee);
        entity.setReporter(reporter);

        return entity;
    }

    private TaskResponse mapEntityToDto(TaskEntity entity) {
        TaskResponse response = new TaskResponse();
        response.setTaskId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setDescription(entity.getDescription());
        response.setStatus(entity.getStatus());
        response.setPriority(entity.getPriority());
        response.setType(entity.getType());
        response.setDueDate(entity.getDueDate());
        response.setAssigneeId(entity.getAssignee().getId());
        response.setProjectId(entity.getProject().getId());
        response.setReporterId(entity.getReporter().getId());
        response.setCreatedAt(entity.getCreatedTime());
        return response;
    }

}
