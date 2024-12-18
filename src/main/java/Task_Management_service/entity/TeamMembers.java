package Task_Management_service.entity;

import Task_Management_service.constant.TeamRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "team_members")
public class TeamMembers extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @Enumerated(EnumType.STRING)
    TeamRole role;


}
