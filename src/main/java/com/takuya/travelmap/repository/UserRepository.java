package com.takuya.travelmap.repository;

import com.takuya.travelmap.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<
        User,
        Integer> {

    // ログインID検索
    User findByUsername(
            String username
    );

}
