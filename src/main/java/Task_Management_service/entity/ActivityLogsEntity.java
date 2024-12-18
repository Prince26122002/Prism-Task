package Task_Management_service.entity;

import Task_Management_service.constant.EntityType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "activity_logs")
public class ActivityLogsEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;
    String action;
    @Enumerated(EnumType.STRING)
    EntityType entityType;
    Long entityId;
}
