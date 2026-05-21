package com.takuya.travelmap.service;

import com.takuya.travelmap.model.User;
import com.takuya.travelmap.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository
            userRepository;

    // コンストラクタDI
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

        // ユーザー存在チェック
        if(user != null &&
           user.getPassword()
               .equals(password)) {

            return user;

        }

        return null;

    }

}
