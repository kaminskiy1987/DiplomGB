package ru.geekbrains.DiplomGBProject.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.DiplomGBProject.entity.Application;
import ru.geekbrains.DiplomGBProject.entity.Status;
import ru.geekbrains.DiplomGBProject.entity.User;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @NonNull
    List<Application> findAll();

    List<Application> getAllByStatus(Status status);

    List<Application> getAllByUser(User user);

    List<Application> getAllByUserAndStatus(User user, Status status);
}