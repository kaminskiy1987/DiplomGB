package ru.geekbrains.DiplomGBProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.DiplomGBProject.dto.SignUpRequest;
import ru.geekbrains.DiplomGBProject.service.AuthenticationService;
import ru.geekbrains.DiplomGBProject.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping()
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegister", new SignUpRequest());
        return "registration";
    }

    @PostMapping("/reg")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               Model model) {
        if (userService.findByUserName(username).isPresent()) {
            model.addAttribute("userRegister", SignUpRequest.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .build());
            model.addAttribute("usernameExists", true);
            model.addAttribute("existingUsername", username);
            return "registration";
        } else {
            authenticationService.signUp(SignUpRequest.builder()
                    .username(username)
                    .password(password)
                    .firstName(firstName)
                    .lastName(lastName)
                    .build()
            );
            return "redirect:login";
        }
    }
}