package Task_Management_service.dto.request;

import Task_Management_service.constant.UserRole;
import Task_Management_service.constant.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserReqDto {
    @Email(message = "Email should be valid")
    String email;
    String username;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password;

    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    String mobileNo;

    UserRole role;
}
