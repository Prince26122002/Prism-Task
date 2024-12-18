package Task_Management_service.dto.response;

import Task_Management_service.constant.UserRole;
import Task_Management_service.constant.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResDto {
    Long id;
    String username;
    String email;
    String mobileNo;
    UserRole role;
    UserStatus status;

}
