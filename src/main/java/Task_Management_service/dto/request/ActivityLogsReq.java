package Task_Management_service.dto.request;

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
public class ActivityLogsReq {
    String action;
    EntityType entityType;
    Long entityId;
}
