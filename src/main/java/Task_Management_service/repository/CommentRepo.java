package Task_Management_service.repository;

import Task_Management_service.entity.CommentEntity;
import Task_Management_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<CommentEntity, Long> {
}
