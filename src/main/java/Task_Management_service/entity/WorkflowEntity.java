package Task_Management_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tb_workflows")
public class WorkflowEntity extends BaseEntity {

    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    ProjectEntity project;
}
