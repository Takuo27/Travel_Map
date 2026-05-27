package com.takuya.travelmap.repository;

import com.takuya.travelmap.model.Like;
import com.takuya.travelmap.model.Post;
import com.takuya.travelmap.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository
        extends JpaRepository<
                Like,
                Integer> {

    // =========================
    // ユーザーがその投稿に
    // いいね済みか確認
    // =========================
    Like findByUserAndPost(

            User user,
            Post post
    );

    // =========================
    // 投稿のいいね数取得
    // =========================
    int countByPost(

            Post post
    );

}
