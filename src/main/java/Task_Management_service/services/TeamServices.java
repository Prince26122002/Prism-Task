package Task_Management_service.services;

import Task_Management_service.dto.request.JwtRequest;
import Task_Management_service.dto.request.TeamReq;
import Task_Management_service.dto.request.UserReqDto;
import Task_Management_service.dto.response.JwtResponse;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.TeamRes;
import Task_Management_service.dto.response.UserResDto;

import java.util.List;

public interface TeamServices {
    TeamRes createTeam(TeamReq teamReq);
    TeamRes updateTeam(Long id,TeamReq teamReq);
    TeamRes getTeamById(Long id);
    PaginatedResp<TeamRes> getAllTeams(int page, int size, String sortBy, String sortDirection);
    void deleteTeamById(Long id);
}
