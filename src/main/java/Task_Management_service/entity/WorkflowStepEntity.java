package Task_Management_service.entity;

import Task_Management_service.constant.WorkflowStepType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tb_workflow_steps")
public class WorkflowStepEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    WorkflowEntity workflow;

    @Column(nullable = false)
    WorkflowStepType type;

    int position;
}
