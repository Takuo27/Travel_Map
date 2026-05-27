package com.takuya.travelmap.service;

import com.takuya.travelmap.model.User;
import com.takuya.travelmap.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    // =========================
    // Repository
    // =========================
    private final UserRepository
            userRepository;

    // =========================
    // パスワード暗号化
    // =========================
    private final BCryptPasswordEncoder
            passwordEncoder =
            new BCryptPasswordEncoder();

    // =========================
    // コンストラクタDI
    // =========================
    public UserService(
            UserRepository
            userRepository
    ) {

        this.userRepository =
                userRepository;

    }

    // =========================
    // ユーザー登録
    // =========================
    public void register(
            User user
    ) {

        // パスワード暗号化
        user.setPassword(

                passwordEncoder.encode(
                        user.getPassword()
                )

        );

        userRepository.save(
                user
        );

    }

    // =========================
    // ログイン判定
    // =========================
    public User login(

            String username,
            String password
    ) {

        User user =
                userRepository
                .findByUsername(
                        username
                );

        // BCrypt比較
        if (user != null &&
            passwordEncoder.matches(
                    password,
                    user.getPassword()
            )) {

            return user;
        }

        return null;

    }

}
