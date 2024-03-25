package ru.geekbrains.DiplomGBProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.DiplomGBProject.entity.Application;
import ru.geekbrains.DiplomGBProject.entity.Status;
import ru.geekbrains.DiplomGBProject.service.ApplicationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/create")
    public void create(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestHeader("Authorization") String authorization) {
        applicationService.save(name, description, authorization);
    }

    @GetMapping("/admin/list")
    public List<Application> listApplication() {
        return applicationService.getAll();
    }

    @GetMapping("/admin/list_by_status")
    public List<Application> listApplicationByStatus(@RequestParam("status") String status) {
        Status s = Status.fromString(status);

        if (s == null) {
            return new ArrayList<>();

        }

        return applicationService.getAllByStatus(s);
    }

    @GetMapping("/user/list")
    public List<Application> listApplicationByUser(@RequestHeader("Authorization") String authorization) {
        return applicationService.getAllByUser(authorization);
    }

    @GetMapping("/user/list_by_status")
    public List<Application> listApplicationByUserAndStatus(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("status") String status) {
        Status s = Status.fromString(status);

        if (status == null) {
            return new ArrayList<>();
        }

        return applicationService.getAllByUserAndStatus(authorization, s);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<String> changeStatusById(@PathVariable("id") Long id, @RequestParam("status") String status) {
        Status s = Status.fromString(status);
        if (s == null) {
            return new ResponseEntity<>("Введен неверный статус", HttpStatus.CONFLICT);

        }

        Optional<Application> application = applicationService.changeStatusById(id, s);

        if (application.isEmpty()) {
            return new ResponseEntity<>("Заявка с данным id не найдена", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Статус успешно изменен", HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        Optional<Application> application = applicationService.deleteById(id);
        if (application.isEmpty()) {
            return new ResponseEntity<>("Заявка с данным id не найдена", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Заявка успешно удалена", HttpStatus.OK);
    }
}