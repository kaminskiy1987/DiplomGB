package ru.geekbrains.DiplomGBProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.DiplomGBProject.dto.SignInRequest;
import ru.geekbrains.DiplomGBProject.service.AuthenticationService;
import ru.geekbrains.DiplomGBProject.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @GetMapping()
    public String getLoginPage(Model model) {
        model.addAttribute("userLogin", new SignInRequest());
        return "login";
    }

    @PostMapping("/log")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        if (userService.findByUserName(username).isPresent()
                && passwordEncoder.matches(password, userService.findByUserName(username).get().getPassword())) {
            model.addAttribute("jwtToken",
                    authenticationService.signIn(SignInRequest.builder()
                            .username(username)
                            .password(password)
                            .build()).getToken());
            return "token";
        } else {
            model.addAttribute("userLogin", new SignInRequest());
            model.addAttribute("notFound", true);
            model.addAttribute("notFoundedUsername", username);
            return "login";
        }
    }
}