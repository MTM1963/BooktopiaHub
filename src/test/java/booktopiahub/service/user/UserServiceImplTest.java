package booktopiahub.service.user;

import static org.mockito.Mockito.when;

import booktopiahub.dto.user.UserRegistrationRequest;
import booktopiahub.dto.user.UserResponseDto;
import booktopiahub.exception.RegistrationException;
import booktopiahub.mapper.UserMapper;
import booktopiahub.model.Role;
import booktopiahub.model.User;
import booktopiahub.repository.user.UserRepository;
import booktopiahub.service.role.RoleService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String VALID_EMAIL = "test@example.com";
    private static final String VALID_PASSWORD = "password";
    private static final String REPEAT_PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final String INVALID_PASSWORD = "another_password";
    private static final Role.RoleName ROLE = Role.RoleName.USER;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSuccessfulRegistration() throws RegistrationException {

        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail(VALID_EMAIL);
        request.setPassword(VALID_PASSWORD);
        request.setRepeatPassword(REPEAT_PASSWORD);

        UserResponseDto expectedResult = new UserResponseDto();
        expectedResult.setEmail(VALID_EMAIL);

        User user = new User();
        user.setEmail(VALID_EMAIL);
        user.setPassword(ENCODED_PASSWORD);

        Role userRole = new Role();
        userRole.setRoleName(ROLE);

        when(userMapper.toModel(request)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectedResult);
        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(VALID_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(roleService.getRoleByRoleName(ROLE)).thenReturn(userRole);
        when(userRepository.save(user)).thenReturn(user);

        UserResponseDto result = userService.register(request);

        Assertions.assertEquals(expectedResult.getEmail(), result.getEmail());
    }

    @Test
    public void testRegistrationWithDuplicateEmail() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail(VALID_EMAIL);

        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(new User()));

        Assertions.assertThrows(RegistrationException.class, () -> userService.register(request));
    }

    @Test
    public void testRegistrationWithMismatchedPasswords() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail(VALID_EMAIL);
        request.setPassword(VALID_PASSWORD);
        request.setRepeatPassword(INVALID_PASSWORD);

        Assertions.assertThrows(RegistrationException.class, () -> userService.register(request));
    }
}
