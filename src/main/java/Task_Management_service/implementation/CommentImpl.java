package Task_Management_service.implementation;

import Task_Management_service.constant.ApiErrorCodes;
import Task_Management_service.dto.request.CommentReq;
import Task_Management_service.dto.response.CommentRes;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.TeamMembersRes;
import Task_Management_service.entity.CommentEntity;
import Task_Management_service.entity.TaskEntity;
import Task_Management_service.entity.TeamMembers;
import Task_Management_service.entity.UserEntity;
import Task_Management_service.exception.NoSuchElementFoundException;
import Task_Management_service.repository.CommentRepo;
import Task_Management_service.repository.TaskRepository;
import Task_Management_service.repository.UserRepo;
import Task_Management_service.services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final TaskRepository taskRepo;
    private final UserRepo userRepo;

    public CommentImpl(CommentRepo commentRepo, TaskRepository taskRepo, UserRepo userRepo) {
        this.commentRepo = commentRepo;
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    @Override
    public CommentRes createComment(CommentReq commentReq) {
        TaskEntity task = taskRepo.findById(commentReq.getTaskId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.TASK_NOT_FOUND.getErrorCode(),ApiErrorCodes.TASK_NOT_FOUND.getErrorMessage()));
        UserEntity user = userRepo.findById(commentReq.getUserId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(),ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));
        CommentEntity commentEntity = mapToEntity(commentReq, task, user);
        CommentEntity savedComment = commentRepo.save(commentEntity);
        return mapToDto(savedComment);
    }

    @Override
    public CommentRes getCommentById(Long id) {
        CommentEntity commentEntity = commentRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        ApiErrorCodes.COMMENT_NOT_FOUND.getErrorCode(),
                        ApiErrorCodes.COMMENT_NOT_FOUND.getErrorMessage()
                ));

        return mapToDto(commentEntity);
    }

    @Override
    public PaginatedResp<CommentRes> getAllComments(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CommentEntity> commentPage = commentRepo.findAll(pageable);

        List<CommentRes> responseList = commentPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                commentPage.getTotalElements(),
                commentPage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public void deleteCommentById(Long id) {
        CommentEntity commentEntity = commentRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.COMMENT_NOT_FOUND.getErrorCode(),ApiErrorCodes.COMMENT_NOT_FOUND.getErrorMessage()));
        commentRepo.delete(commentEntity);
    }

    private CommentRes mapToDto(CommentEntity commentEntity) {
        CommentRes commentRes = new CommentRes();
        commentRes.setId(commentEntity.getId());
        commentRes.setContent(commentEntity.getContent());
        commentRes.setUserId(commentEntity.getUser().getId());
        commentRes.setUserName(commentEntity.getUser().getUsername());
        commentRes.setTaskId(commentEntity.getTask().getId());
        commentRes.setTaskTitle(commentEntity.getTask().getTitle());

        return commentRes;
    }

    private CommentEntity mapToEntity(CommentReq commentReq, TaskEntity task, UserEntity user) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentReq.getContent());
        commentEntity.setTask(task);
        commentEntity.setUser(user);

        return commentEntity;
    }
}
