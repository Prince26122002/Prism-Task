package Task_Management_service.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectResponse {
    Long projectId;
    String name;
    String description;
    Long ownerId;
    String ownerName;
    Long teamId;
    String teamName;
    Date createdAt;
}
