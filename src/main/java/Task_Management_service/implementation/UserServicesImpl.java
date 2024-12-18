package Task_Management_service.implementation;

import Task_Management_service.AuthUtils.JwtHelper;
import Task_Management_service.config.AuthConfig;
import Task_Management_service.constant.ApiErrorCodes;
import Task_Management_service.constant.UserStatus;
import Task_Management_service.dto.request.JwtRequest;
import Task_Management_service.dto.request.UserReqDto;
import Task_Management_service.dto.response.JwtResponse;
import Task_Management_service.dto.response.PaginatedResp;
import Task_Management_service.dto.response.TeamMembersRes;
import Task_Management_service.dto.response.UserResDto;
import Task_Management_service.entity.TeamMembers;
import Task_Management_service.entity.UserEntity;
import Task_Management_service.exception.NoSuchElementFoundException;
import Task_Management_service.exception.ValidationException;
import Task_Management_service.repository.UserRepo;
import Task_Management_service.services.UserServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepo userRepo;
    private final AuthConfig authConfig;
    private final JwtHelper jwtHelper;

    public UserServicesImpl(UserRepo userRepo, AuthConfig authConfig, JwtHelper jwtHelper) {
        this.userRepo = userRepo;
        this.authConfig = authConfig;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public UserResDto createUser(UserReqDto userReqDto) {
        Optional<UserEntity> optionalUserEntity = userRepo.findByEmail(userReqDto.getEmail());
        if (optionalUserEntity.isPresent()) {
            throw new ValidationException(
                    ApiErrorCodes.USER_ALREADY_EXIST.getErrorCode(),ApiErrorCodes.USER_ALREADY_EXIST.getErrorMessage());
        }
        Optional<UserEntity> optionalUserByMobile = userRepo.findByMobileNo(userReqDto.getMobileNo());
        if (optionalUserByMobile.isPresent()) {
            throw new ValidationException(
                    ApiErrorCodes.MOBILE_ALREADY_EXIST.getErrorCode(),ApiErrorCodes.MOBILE_ALREADY_EXIST.getErrorMessage());
        }
        UserEntity user = mapToEntity(userReqDto);
        user.setStatus(UserStatus.ACTIVE);
        UserEntity savedUser = userRepo.save(user);
        return mapToDto(savedUser);
    }

    @Override
    public UserResDto updateUser(Long id, UserReqDto userReqDto) {
        UserEntity userEntity = userRepo.findUserById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(),ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()));
        Optional<UserEntity> existingUser = userRepo.findByEmail(userReqDto.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new ValidationException(ApiErrorCodes.USER_ALREADY_EXIST.getErrorCode(),ApiErrorCodes.USER_ALREADY_EXIST.getErrorMessage());
        }
        userEntity.setEmail(userReqDto.getEmail());
        userEntity.setMobileNo(userReqDto.getMobileNo());
        userEntity.setRole(userReqDto.getRole());

        if (userReqDto.getPassword() != null && !userReqDto.getPassword().isBlank()) {
            userEntity.setPassword(authConfig.passwordEncoder().encode(userReqDto.getPassword()));
        }
        UserEntity updatedUser = userRepo.save(userEntity);
        return mapToDto(updatedUser);
    }


    @Override
    public void deleteUserById(Long id) {
        UserEntity userEntity = userRepo.findUserById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(),ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()
                ));
        userEntity.setStatus(UserStatus.INACTIVE);
        userRepo.save(userEntity);
    }

    @Override
    public JwtResponse loginUser(JwtRequest jwtRequest) {
        UserDetails userDetails = loadUserByUsername(jwtRequest.getMobileNo());
        if (AuthConfig.matches(jwtRequest.getPassword(), userDetails.getPassword())) {
            String token = jwtHelper.generateToken(userDetails);
            return new JwtResponse(token);
        }

        throw new ValidationException(
                ApiErrorCodes.INVALID_MOBILE_OR_PASSWORD.getErrorCode(),  ApiErrorCodes.INVALID_MOBILE_OR_PASSWORD.getErrorMessage()
        );
    }

    @Override
    public UserResDto getUserById(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
        if (optionalUserEntity.isEmpty()) {
            throw new NoSuchElementFoundException(
                    ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage()
            );
        }

        return mapToDto(optionalUserEntity.get());
    }

    @Override
    public UserDetails loadUserByUsername(String mobileNo) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepo.findByMobileNo(mobileNo);
        if (optionalUserEntity.isPresent()) {
            UserEntity user = optionalUserEntity.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getMobileNo(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")) // Default authority
            );
        }

        throw new ValidationException(ApiErrorCodes.INVALID_MOBILE_OR_PASSWORD.getErrorCode(),ApiErrorCodes.INVALID_MOBILE_OR_PASSWORD.getErrorMessage());
    }


    @Override
    public PaginatedResp<UserResDto> getAllUsers(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserEntity> userPage = userRepo.findAll(pageable);

        List<UserResDto> responseList = userPage.getContent()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new PaginatedResp<>(
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                page,
                responseList
        );
    }


    private UserResDto mapToDto(UserEntity userEntity) {
        UserResDto userResDto = new UserResDto();
        userResDto.setId(userEntity.getId());
        userResDto.setEmail(userEntity.getEmail());
        userResDto.setUsername(userEntity.getUsername());
        userResDto.setMobileNo(userEntity.getMobileNo());
        userResDto.setRole(userEntity.getRole());
        userResDto.setStatus(userEntity.getStatus());

        return userResDto;
    }

    private UserEntity mapToEntity(UserReqDto userReqDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userReqDto.getEmail());
        userEntity.setUsername(userReqDto.getUsername());
        userEntity.setPassword(authConfig.passwordEncoder().encode(userReqDto.getPassword()));
        userEntity.setMobileNo(userReqDto.getMobileNo());
        userEntity.setRole(userReqDto.getRole());
        return userEntity;
    }
}