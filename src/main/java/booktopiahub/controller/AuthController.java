package booktopiahub.controller;

import booktopiahub.dto.user.UserLoginRequestDto;
import booktopiahub.dto.user.UserLoginResponseDto;
import booktopiahub.dto.user.UserRegistrationRequest;
import booktopiahub.dto.user.UserResponseDto;
import booktopiahub.exception.RegistrationException;
import booktopiahub.security.AuthenticationService;
import booktopiahub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequest request)
            throws RegistrationException {
        return userService.register(request);
    }

    @GetMapping("/getlogin")
    public String getLogin() {
        return "Hello";
    }
}
