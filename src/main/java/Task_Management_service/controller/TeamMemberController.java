package Task_Management_service.controller;

import Task_Management_service.dto.request.TeamMembersReq;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.TeamMembersRes;
import Task_Management_service.dto.response.TeamRes;
import Task_Management_service.dto.response.UserResDto;
import Task_Management_service.services.TeamMembersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teamMember")
public class TeamMemberController {
    @Autowired
    private final TeamMembersServices teamMembersServices;

    public TeamMemberController(TeamMembersServices teamMembersServices) {
        this.teamMembersServices = teamMembersServices;
    }

    @PostMapping("/create")
    public ResponseEntity<TeamMembersRes> createTeamMember(@RequestBody TeamMembersReq teamMembersReq) {
        TeamMembersRes reqs = teamMembersServices.createTeamMember(teamMembersReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(reqs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMembersRes> getTeamMemberById(@PathVariable Long id ){
        TeamMembersRes resp = teamMembersServices.getTeamMemberById(id);
        return  ResponseEntity.status(HttpStatus.OK).body(resp);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TeamMembersRes> updateTeamMembers(@Validated @RequestBody TeamMembersReq teamMembersReq,@PathVariable Long id) {
        TeamMembersRes updatedMember = teamMembersServices.updateTeamMember(id, teamMembersReq);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMember);
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResp<TeamMembersRes>> getAllTeamMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(teamMembersServices.getAllTeamMembers(page, size, sortBy, sortDirection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamMemberById(@PathVariable Long id) {
        teamMembersServices.deleteTeamMemberById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
