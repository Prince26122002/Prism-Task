package Task_Management_service.services;

import Task_Management_service.dto.request.JwtRequest;
import Task_Management_service.dto.request.UserReqDto;
import Task_Management_service.dto.response.JwtResponse;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.TeamRes;
import Task_Management_service.dto.response.UserResDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServices extends UserDetailsService {
    UserResDto createUser(UserReqDto userReqDto);
    JwtResponse loginUser(JwtRequest jwtRequest);
    UserResDto updateUser(Long id, UserReqDto userReqDto);

    UserResDto getUserById(Long id);

    PaginatedResp<UserResDto> getAllUsers(int page, int size, String sortBy, String sortDirection);
    void deleteUserById(Long id);
}
