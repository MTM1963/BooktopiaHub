package booktopiahub.service;

import booktopiahub.dto.user.UserDto;
import booktopiahub.dto.user.UserRegistrationRequest;
import booktopiahub.dto.user.UserResponseDto;
import booktopiahub.exception.RegistrationException;
import java.util.Optional;

public interface UserService {
    UserDto get(Long id);

    Optional<UserDto> findByEmail(String email);

    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;
}
