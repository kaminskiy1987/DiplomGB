package ru.geekbrains.DiplomGBProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.DiplomGBProject.dto.SignInRequest;
import ru.geekbrains.DiplomGBProject.dto.SignUpRequest;
import ru.geekbrains.DiplomGBProject.entity.Role;
import ru.geekbrains.DiplomGBProject.entity.User;
import ru.geekbrains.DiplomGBProject.security.JwtAuthenticationResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void signUp(SignUpRequest request) {
        String firstName = request.getFirstName().substring(0, 1).toUpperCase()
                + request.getFirstName().substring(1).toLowerCase();
        String lastName = request.getLastName().substring(0, 1).toUpperCase()
                + request.getLastName().substring(1).toLowerCase();

        User user = User.builder()
                .username(request.getUsername().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(firstName)
                .lastName(lastName)
                .role(Role.ROLE_USER)
                .build();
        userService.save(user);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername().toLowerCase(),
                request.getPassword()
        ));

        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername().toLowerCase());

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}