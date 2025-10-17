package com.example.prodBack8.services.auth;


import com.example.prodBack8.dto.auth.LoginRequest;
import com.example.prodBack8.dto.auth.LoginResponse;
import com.example.prodBack8.dto.auth.RegisterRequest;
import com.example.prodBack8.exceptions.UserAlreadyExistsException;
import com.example.prodBack8.exceptions.UserNotFoundException;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.model.entity.user.UserRole;
import com.example.prodBack8.repository.UserRepository;
import com.example.prodBack8.services.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public LoginResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с email " + request.getUsername() + " уже сущетвует");
        }
        var user = UserEntity.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(UserRole.valueOf(String.valueOf(request.getRole())))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public LoginResponse authenticate(LoginRequest request) {
        String username = request.getUsername();
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User with email -" + username + " not found");
        }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByUsername(username).orElseThrow();
            var jwtToken = jwtService.generateToken(user);

            return LoginResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        }
}