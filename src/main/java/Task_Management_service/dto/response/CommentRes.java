package Task_Management_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRes {
    Long id;
    Long taskId;
    String taskTitle;
    Long userId;
    String userName;
    String content;
}
