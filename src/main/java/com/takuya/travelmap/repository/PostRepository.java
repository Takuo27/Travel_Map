package com.takuya.travelmap.repository;

import com.takuya.travelmap.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface PostRepository
        extends JpaRepository<Post, Integer> {

    // =========================
    // 投稿内容部分一致検索
    // =========================
    List<Post> findByContentContaining(
            String keyword
    );

    // =========================
    // 重複除外件数取得
    // =========================
    @Query(
        "SELECT COUNT(DISTINCT p.content) FROM Post p"
    )
    long countDistinctContent();
}