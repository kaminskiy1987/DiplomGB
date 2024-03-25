package ru.geekbrains.DiplomGBProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.DiplomGBProject.entity.User;
import ru.geekbrains.DiplomGBProject.service.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        Optional<User> user = userService.deleteById(id);
        if (user.isPresent() && user.get().getId() != 1) {
            return new ResponseEntity<>("Пользователь успешно удален", HttpStatus.OK);
        }
        return new ResponseEntity<>("Пользователь с данным id не найден", HttpStatus.CONFLICT);
    }
}
