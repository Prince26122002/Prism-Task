package Task_Management_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "comment_entity")
public class CommentEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    TaskEntity task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;
    String content;

}
