package Task_Management_service.services;

import Task_Management_service.dto.request.TeamMembersReq;
import Task_Management_service.dto.request.TeamReq;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.TeamMembersRes;
import Task_Management_service.dto.response.TeamRes;
import Task_Management_service.dto.response.UserResDto;

import java.util.List;

public interface TeamMembersServices {
    TeamMembersRes createTeamMember(TeamMembersReq teamMembersReq);
    TeamMembersRes updateTeamMember(Long id,TeamMembersReq teamMembersReq);

    TeamMembersRes getTeamMemberById(Long id);
    PaginatedResp<TeamMembersRes> getAllTeamMembers(int page, int size, String sortBy, String sortDirection);
    void deleteTeamMemberById(Long id);
}
