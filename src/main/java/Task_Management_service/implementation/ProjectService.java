package Task_Management_service.implementation;

import Task_Management_service.constant.ApiErrorCodes;
import Task_Management_service.dto.request.ProjectRequest;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.ProjectResponse;
import Task_Management_service.entity.ProjectEntity;
import Task_Management_service.entity.TeamEntity;
import Task_Management_service.entity.UserEntity;
import Task_Management_service.exception.NoSuchElementFoundException;
import Task_Management_service.repository.ProjectRepository;
import Task_Management_service.repository.TeamRepo;
import Task_Management_service.repository.UserRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepo userRepository;
    private final TeamRepo teamRepo;

    public ProjectService(ProjectRepository projectRepository, UserRepo userRepository, TeamRepo teamRepo) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.teamRepo = teamRepo;
    }

    public ProjectResponse createProject(ProjectRequest request) {
        UserEntity owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.OWNER_NOT_FOUND.getErrorCode(), ApiErrorCodes.OWNER_NOT_FOUND.getErrorMessage()));

        TeamEntity team = teamRepo.findById(request.getTeamId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.TEAM_NOT_FOUND.getErrorCode(), ApiErrorCodes.TEAM_NOT_FOUND.getErrorMessage()));

        ProjectEntity entity = mapDtoToEntity(request, owner, team);
        return mapEntityToDto(projectRepository.save(entity));
    }

    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PROJECT_NOT_FOUND.getErrorCode(),ApiErrorCodes.PROJECT_NOT_FOUND.getErrorMessage()));

        UserEntity owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.OWNER_NOT_FOUND.getErrorCode(), ApiErrorCodes.OWNER_NOT_FOUND.getErrorMessage()));

        TeamEntity team = teamRepo.findById(request.getTeamId())
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.TEAM_NOT_FOUND.getErrorCode(), ApiErrorCodes.TEAM_NOT_FOUND.getErrorMessage()));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwner(owner);
        project.setTeam(team);

        return mapEntityToDto(projectRepository.save(project));
    }

    public ProjectResponse getProjectById(Long id) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PROJECT_NOT_FOUND.getErrorCode(),ApiErrorCodes.PROJECT_NOT_FOUND.getErrorMessage()));
        return mapEntityToDto(project);
    }

    public void deleteProject(Long id) {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.PROJECT_NOT_FOUND.getErrorCode(),ApiErrorCodes.PROJECT_NOT_FOUND.getErrorMessage()));
        projectRepository.delete(project);
    }

    public PaginatedResp<ProjectResponse> getAllProjects(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortBy);

        var paginatedResult = projectRepository.findAll(pageRequest);
        List<ProjectResponse> projectResponses = paginatedResult.getContent().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(paginatedResult.getTotalElements(), paginatedResult.getTotalPages(), page, projectResponses);
    }

    private ProjectEntity mapDtoToEntity(ProjectRequest request, UserEntity owner, TeamEntity team) {
        ProjectEntity entity = new ProjectEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setOwner(owner);
        entity.setTeam(team);
        return entity;
    }

    private ProjectResponse mapEntityToDto(ProjectEntity entity) {
        ProjectResponse response = new ProjectResponse();
        response.setProjectId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setOwnerId(entity.getOwner().getId());
        response.setOwnerName(entity.getOwner().getUsername());
        response.setTeamId(entity.getTeam().getId());
        response.setTeamName(entity.getTeam().getTeamName());
        response.setCreatedAt(entity.getCreatedTime());
        return response;
    }
}
