package Task_Management_service.implementation;

import Task_Management_service.constant.ApiErrorCodes;
import Task_Management_service.constant.EntityType;
import Task_Management_service.dto.request.ActivityLogsReq;
import Task_Management_service.dto.response.ActivityLogsRes;
import Task_Management_service.dto.response.CommentRes;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.entity.*;
import Task_Management_service.exception.NoSuchElementFoundException;
import Task_Management_service.repository.*;
import Task_Management_service.services.ActivityLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static Task_Management_service.constant.EntityType.*;

@Service
public class ActivityLogsImpl implements ActivityLogService {
    private final ActivityLogsRepo activityLogsRepo;
    private final UserRepo userRepo;
    private final ProjectRepository projectRepo;
    private final WorkflowRepository workflowRepo;
    private final CommentRepo commentRepo;

    public ActivityLogsImpl(ActivityLogsRepo activityLogsRepo, UserRepo userRepo, ProjectRepository projectRepo, WorkflowRepository workflowRepo, CommentRepo commentRepo) {
        this.activityLogsRepo = activityLogsRepo;
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
        this.workflowRepo = workflowRepo;
        this.commentRepo = commentRepo;

    }

    @Override
    public ActivityLogsRes createActivityLogs(ActivityLogsReq activityLogsReq) {
        validateEntityId(activityLogsReq.getEntityType(), activityLogsReq.getEntityId());

        UserEntity user = fetchUserByEntity(activityLogsReq.getEntityType(), activityLogsReq.getEntityId());
        ActivityLogsEntity activityLogsEntity = mapToEntity(activityLogsReq, user);
        ActivityLogsEntity savedActivityLogs = activityLogsRepo.save(activityLogsEntity);
        return mapToDto(savedActivityLogs);
    }

    private void validateEntityId(EntityType entityType, Long entityId) {
        boolean exists = switch (entityType) {
            case Project -> projectRepo.existsById(entityId);
            case Workflow -> workflowRepo.existsById(entityId);
            case Comment -> commentRepo.existsById(entityId);
            default -> false;
        };

        if (!exists) {
            throw new NoSuchElementFoundException(
                    ApiErrorCodes.INVALID_ENTITY_ID.getErrorCode(),
                    String.format("ID %d does not exist for entity type %s", entityId, entityType)
            );
        }
    }

    private UserEntity fetchUserByEntity(EntityType entityType, Long entityId) {
        return switch (entityType) {
            case Project -> projectRepo.findById(entityId)
                    .map(ProjectEntity::getOwner)
                    .orElseThrow(() -> new NoSuchElementFoundException(
                            ApiErrorCodes.INVALID_ENTITY_ID.getErrorCode(), "Project ID does not exist"));
            case Workflow -> workflowRepo.findById(entityId)
                    .map(workflow -> {
                        ProjectEntity project = workflow.getProject();
                        if (project == null || project.getOwner() == null) {
                            throw new NoSuchElementFoundException(
                                    ApiErrorCodes.INVALID_ENTITY_ID.getErrorCode(), "Workflow's Project does not have a valid Owner");
                        }
                        return project.getOwner();
                    })
                    .orElseThrow(() -> new NoSuchElementFoundException(
                            ApiErrorCodes.INVALID_ENTITY_ID.getErrorCode(), "Workflow ID does not exist"));
            case Comment -> commentRepo.findById(entityId)
                    .map(CommentEntity::getUser)
                    .orElseThrow(() -> new NoSuchElementFoundException(
                            ApiErrorCodes.INVALID_ENTITY_ID.getErrorCode(), "Comment ID does not exist"));
            default -> throw new NoSuchElementFoundException(
                    ApiErrorCodes.INVALID_ENTITY_ID.getErrorCode(), "Invalid EntityType"
            );
        };
    }

    @Override
    public ActivityLogsRes getActivityLogsById(Long id) {
        ActivityLogsEntity activityLogsEntity = activityLogsRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException( ApiErrorCodes.ACTIVITY_LOGS_NOT_FOUND.getErrorCode(),ApiErrorCodes.ACTIVITY_LOGS_NOT_FOUND.getErrorMessage()));

        return mapToDto(activityLogsEntity);
    }


    @Override
    public PaginatedResp<ActivityLogsRes> getAllActivityLogs(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ActivityLogsEntity> activityLogsPage = activityLogsRepo.findAll(pageable);

        List<ActivityLogsRes> responseList = activityLogsPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                activityLogsPage.getTotalElements(),
                activityLogsPage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public void deleteActivityLogsById(Long id) {
        ActivityLogsEntity activityLogsEntity = activityLogsRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.ACTIVITY_LOGS_NOT_FOUND.getErrorCode(),ApiErrorCodes.ACTIVITY_LOGS_NOT_FOUND.getErrorMessage()));
        activityLogsRepo.delete(activityLogsEntity);
    }

    private ActivityLogsRes mapToDto(ActivityLogsEntity activityLogsEntity) {
        ActivityLogsRes activityLogsRes = new ActivityLogsRes();
        activityLogsRes.setId(activityLogsEntity.getId());
        activityLogsRes.setEntityType(activityLogsEntity.getEntityType());
        activityLogsRes.setUserId(activityLogsEntity.getUser().getId());
        activityLogsRes.setUserName(activityLogsEntity.getUser().getUsername());
        activityLogsRes.setAction(activityLogsEntity.getAction());
        activityLogsRes.setEntityId(activityLogsEntity.getEntityId());

        return activityLogsRes;
    }

    private ActivityLogsEntity mapToEntity(ActivityLogsReq activityLogsReq, UserEntity user) {
        ActivityLogsEntity activityLogsEntity = new ActivityLogsEntity();
        activityLogsEntity.setEntityType(activityLogsReq.getEntityType());
        activityLogsEntity.setAction(activityLogsReq.getAction());
        activityLogsEntity.setEntityId(activityLogsReq.getEntityId());
        activityLogsEntity.setUser(user);

        return activityLogsEntity;
    }
}
