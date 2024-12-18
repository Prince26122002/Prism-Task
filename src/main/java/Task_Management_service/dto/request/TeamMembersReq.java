package Task_Management_service.dto.request;

import Task_Management_service.constant.TeamRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamMembersReq {
    Long teamId;
    Long userId;
    TeamRole role;
}
