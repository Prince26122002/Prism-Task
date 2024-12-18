package Task_Management_service.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Entity
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tb_projects")
public class ProjectEntity extends BaseEntity {

     @Column(name = "name", nullable = false)
     String name;

     @Column(name = "description", columnDefinition = "TEXT")
     String description;

     @ManyToOne
     @JoinColumn(name = "owner_id", nullable = false)
     UserEntity owner;

     @ManyToOne
     @JoinColumn(name = "team_id", nullable = false)
     TeamEntity team;
}