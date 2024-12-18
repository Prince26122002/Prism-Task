package Task_Management_service.dto.response;

import Task_Management_service.constant.TeamRole;
import Task_Management_service.entity.UserEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamMembersRes {
    Long id;
    Long teamId;
    String teamName;
    Long userId;
    String userName;
    TeamRole role;
}
