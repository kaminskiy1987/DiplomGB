package ru.geekbrains.DiplomGBProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.DiplomGBProject.entity.Application;
import ru.geekbrains.DiplomGBProject.entity.Status;
import ru.geekbrains.DiplomGBProject.entity.User;
import ru.geekbrains.DiplomGBProject.repository.ApplicationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.geekbrains.DiplomGBProject.security.JwtAuthenticationFilter.BEARER_PREFIX;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final JwtService jwtService;

    public List<Application> getAll() {
        return applicationRepository.findAll();
    }

    public List<Application> getAllByStatus(Status status) {
        return applicationRepository.getAllByStatus(status);
    }

    public List<Application> getAllByUser(String authorization) {
        return applicationRepository.getAllByUser(getUserFromToken(authorization));
    }

    public List<Application> getAllByUserAndStatus(String authorization, Status status) {
        return applicationRepository.getAllByUserAndStatus(getUserFromToken(authorization), status);
    }

    public Optional<Application> deleteById(Long id) {
        Optional<Application> application = applicationRepository.findById(id);
        if (applicationRepository.findById(id).isPresent()) {
            applicationRepository.deleteById(id);
        }
        return application;
    }

    public void save(String name, String description, String authorization) {
        applicationRepository.save(Application.builder()
                .name(name)
                .description(description)
                .creationDate(LocalDateTime.now())
                .status(Status.NOT_STARTED)
                .user(getUserFromToken(authorization))
                .build());
    }

    public Optional<Application> changeStatusById(Long id, Status status) {
        Optional<Application> application = applicationRepository.findById(id);
        if (application.isPresent()) {
            application.get().setStatus(status);
            applicationRepository.save(application.get());
        }
        return application;
    }

    public User getUserFromToken(String authorization) {
        String jwt = authorization.substring(BEARER_PREFIX.length());
        String username = jwtService.extractUsername(jwt);
        return userService.findByUserName(username).get();
    }
}