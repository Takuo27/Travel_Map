package com.takuya.travelmap.controller;

import com.takuya.travelmap.model.User;
import com.takuya.travelmap.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UserController {

    private final UserService
            userService;

    public UserController(

            UserService
            userService
    ) {

        this.userService =
                userService;

    }

    // =========================
    // 登録画面
    // =========================
    @GetMapping("/register")
    public String registerPage(
            Model model
    ) {

        model.addAttribute(
                "user",
                new User()
        );

        return "register";

    }

    // =========================
    // 登録処理
    // =========================
    @PostMapping("/register")
    public String register(

            @ModelAttribute
            User user
    ) {

        userService.register(
                user
        );

        return "redirect:/login";

    }

    // =========================
    // ログイン画面
    // =========================
    @GetMapping("/login")
    public String loginPage() {

        return "login";

    }

    // =========================
    // ログイン処理
    // =========================
    @PostMapping("/login")
    public String login(

            String username,
            String password,

            HttpSession
            session
    ) {

        User user =
                userService.login(
                        username,
                        password
                );

        if(user != null){

            session.setAttribute(
                    "loginUser",
                    user
            );

            return "redirect:/";

        }

        return "redirect:/login";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}