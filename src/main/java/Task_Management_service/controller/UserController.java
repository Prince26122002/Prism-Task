package Task_Management_service.controller;

import Task_Management_service.dto.request.JwtRequest;
import Task_Management_service.dto.request.UserReqDto;
import Task_Management_service.dto.response.JwtResponse;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.TeamMembersRes;
import Task_Management_service.dto.response.UserResDto;
import Task_Management_service.services.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<UserResDto> registerUser(@RequestBody @Valid UserReqDto userReqDto) {
        UserResDto userResDto = userServices.createUser(userReqDto);
        return new ResponseEntity<>(userResDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = userServices.loginUser(jwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResDto> updateUser(@PathVariable Long id,@RequestBody UserReqDto userReqDto) {
        UserResDto updatedUser = userServices.updateUser(id, userReqDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResDto> getUserById(@PathVariable Long id) {
        UserResDto userResDto = userServices.getUserById(id);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedResp<UserResDto>> getAllTeamMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(userServices.getAllUsers(page, size, sortBy, sortDirection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userServices.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
