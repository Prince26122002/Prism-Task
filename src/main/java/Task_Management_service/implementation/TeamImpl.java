package Task_Management_service.implementation;

import Task_Management_service.AuthUtils.JwtHelper;
import Task_Management_service.config.AuthConfig;
import Task_Management_service.constant.ApiErrorCodes;
import Task_Management_service.constant.UserStatus;
import Task_Management_service.dto.request.*;
import Task_Management_service.dto.response.*;
import Task_Management_service.entity.TaskEntity;
import Task_Management_service.entity.TeamEntity;
import Task_Management_service.entity.TeamMembers;
import Task_Management_service.entity.UserEntity;
import Task_Management_service.exception.NoSuchElementFoundException;
import Task_Management_service.exception.ValidationException;
import Task_Management_service.repository.TeamMembersRepo;
import Task_Management_service.repository.TeamRepo;
import Task_Management_service.repository.UserRepo;
import Task_Management_service.services.TeamServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamImpl implements TeamServices {
    private final TeamRepo teamRepo;

    public TeamImpl(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }


    @Override
    public TeamRes createTeam(TeamReq teamReq) {
        Optional<TeamEntity> optionalTeamEntity = teamRepo.findByTeamName(teamReq.getTeamName());
        if (optionalTeamEntity.isPresent()) {
            throw new ValidationException(
                    ApiErrorCodes.TEAM_ALREADY_EXIST.getErrorCode(),ApiErrorCodes.TEAM_ALREADY_EXIST.getErrorMessage()
            );
        }
        TeamEntity teamEntity = mapToEntity(teamReq);
        TeamEntity savedTeam = teamRepo.save(teamEntity);
        return mapToDto(savedTeam);

    }
    @Override
    public TeamRes updateTeam(Long id, TeamReq teamReq) {
        TeamEntity teamEntity = teamRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.TEAM_NOT_FOUND.getErrorCode(), ApiErrorCodes.TEAM_NOT_FOUND.getErrorMessage()));
        teamEntity.setTeamName(teamReq.getTeamName());
        teamEntity.setDescription(teamReq.getDescription());
        return mapToDto(teamRepo.save(teamEntity));
    }

    @Override
    public TeamRes getTeamById(Long id) {
        Optional<TeamEntity> optionalTeamEntity = teamRepo.findById(id);
        if (optionalTeamEntity.isEmpty()) {
            throw new NoSuchElementFoundException(
                    ApiErrorCodes.TEAM_NOT_FOUND.getErrorCode(), ApiErrorCodes.TEAM_NOT_FOUND.getErrorMessage()
            );
        }

        return mapToDto(optionalTeamEntity.get());
    }


    @Override
    public PaginatedResp<TeamRes> getAllTeams(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TeamEntity> teamPage = teamRepo.findAll(pageable);

        List<TeamRes> responseList = teamPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                teamPage.getTotalElements(),
                teamPage.getTotalPages(),
                page,
                responseList
        );
    }

    @Override
    public void deleteTeamById(Long id) {
        Optional<TeamEntity> optionalTeamEntity = teamRepo.findById(id);
        if(optionalTeamEntity.isEmpty()){
            throw new NoSuchElementFoundException(ApiErrorCodes.TEAM_NOT_FOUND.getErrorCode(), ApiErrorCodes.TEAM_NOT_FOUND.getErrorMessage());
        }
        teamRepo.delete(optionalTeamEntity.get());
    }


    private TeamRes mapToDto(TeamEntity teamEntity) {
        TeamRes teamRes = new TeamRes();
        teamRes.setId(teamEntity.getId());
        teamRes.setTeamName(teamEntity.getTeamName());
        teamRes.setDescription(teamEntity.getDescription());

        return teamRes;
    }

    private TeamEntity mapToEntity(TeamReq teamReq) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamName(teamReq.getTeamName());
        teamEntity.setDescription(teamReq.getDescription());
        return teamEntity;
    }
}
