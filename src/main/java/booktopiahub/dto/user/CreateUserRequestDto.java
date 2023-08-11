package booktopiahub.dto.user;

import booktopiahub.model.Role;
import jakarta.persistence.Column;
import java.util.Set;
import lombok.Data;

@Data
public class CreateUserRequestDto {
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String shippingAddress;
    private Set<Role> roles;
}
