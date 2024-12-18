package Task_Management_service.dto.response;

import Task_Management_service.constant.EntityType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityLogsRes {
    Long id;
    Long userId;
    String userName;
    String action;
    EntityType entityType;
    Long entityId;
}
