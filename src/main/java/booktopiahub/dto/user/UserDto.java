package booktopiahub.dto.user;

import booktopiahub.model.Role;
import java.util.Set;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Set<Role> roles;
}
