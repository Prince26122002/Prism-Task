package Task_Management_service.entity;

import Task_Management_service.constant.TaskPriority;
import Task_Management_service.constant.TaskStatus;
import Task_Management_service.constant.TaskType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "task_entity")
public class TaskEntity extends BaseEntity {
    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    TaskPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    TaskType type;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    UserEntity assignee;  // assignee take by user-entity

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    UserEntity reporter;  // reporter take by user_entity

    @Column(name = "due_date")
    Date dueDate;;
}
