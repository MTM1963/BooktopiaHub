package booktopiahub.service;

import booktopiahub.dto.user.CreateUserRequestDto;
import booktopiahub.dto.user.UserDto;
import booktopiahub.dto.user.UserRegistrationRequest;
import booktopiahub.dto.user.UserResponseDto;
import booktopiahub.exception.EntityNotFoundException;
import booktopiahub.exception.RegistrationException;
import booktopiahub.mapper.UserMapper;
import booktopiahub.model.User;
import booktopiahub.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto add(CreateUserRequestDto requestDto) {
        User user = userMapper.toModel(requestDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by id " + id));
        return userMapper.toDto(user);
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto);
    }

    @Override
    public UserResponseDto register(UserRegistrationRequest request) throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
