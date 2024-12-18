package Task_Management_service.repository;

import Task_Management_service.entity.TeamEntity;
import Task_Management_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepo extends JpaRepository<TeamEntity, Long> {
    Optional<TeamEntity> findByTeamName(String teamName);
    Optional<TeamEntity> findById(Long id);

}
