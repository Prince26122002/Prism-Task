package Task_Management_service.repository;

import Task_Management_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByMobileNo(String mobileNo);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findUserById(Long id);
}
