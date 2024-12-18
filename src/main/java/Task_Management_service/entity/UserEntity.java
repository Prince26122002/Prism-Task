package Task_Management_service.entity;

import Task_Management_service.constant.UserRole;
import Task_Management_service.constant.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_entity")
public class UserEntity extends BaseEntity{
    String username;
    String password;
    String email;
    String mobileNo;
    @Enumerated(EnumType.STRING)
    UserRole role;
    @Enumerated(EnumType.STRING)
    UserStatus status;

}
