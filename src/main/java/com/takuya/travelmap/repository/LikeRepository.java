package com.takuya.travelmap.repository;

import com.takuya.travelmap.model.Like;
import com.takuya.travelmap.model.Post;
import com.takuya.travelmap.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    // =========================
    // いいね済み確認
    // =========================
    Optional<Like> findByUserAndPost(User user, Post post);

    // =========================
    // いいね数取得
    // =========================
    long countByPost(Post post);
}
