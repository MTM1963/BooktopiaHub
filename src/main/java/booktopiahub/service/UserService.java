package booktopiahub.service;

import booktopiahub.dto.user.UserRegistrationRequest;
import booktopiahub.dto.user.UserResponseDto;
import booktopiahub.exception.RegistrationException;

public interface UserService {

    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;
}
