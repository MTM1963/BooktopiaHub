package booktopiahub.dto.user;

import booktopiahub.lib.FieldsValueMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
@Data
public class UserRegistrationRequest {
    @NotBlank
    @Size(min = 8, max = 50)
    private String email;
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
    private String repeatPassword;
    @NotBlank
    @Size(min = 2, max = 35)
    private String firstName;
    @Size(min = 2, max = 35)
    private String lastName;
    @NotBlank
    @Size(min = 5, max = 100)
    private String shippingAddress;
}
