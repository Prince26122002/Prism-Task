package Task_Management_service.repository;

import Task_Management_service.entity.TeamEntity;
import Task_Management_service.entity.TeamMembers;
import Task_Management_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMembersRepo extends JpaRepository<TeamMembers, Long> {
    Optional<TeamMembers> findTeamMemberById(Long id);
    Optional<TeamMembers> findByTeamAndUser(TeamEntity team, UserEntity user);


}
