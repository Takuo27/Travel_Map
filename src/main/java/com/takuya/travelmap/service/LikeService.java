package com.takuya.travelmap.service;

import com.takuya.travelmap.model.Like;
import com.takuya.travelmap.model.Post;
import com.takuya.travelmap.model.User;

import com.takuya.travelmap.repository.LikeRepository;
import com.takuya.travelmap.repository.PostRepository;

import org.springframework.stereotype.Service;

@Service
public class LikeService {

    // =========================
    // Repository
    // =========================
    private final LikeRepository
            likeRepository;

    private final PostRepository
            postRepository;

    // =========================
    // コンストラクタDI
    // =========================
    public LikeService(

            LikeRepository
            likeRepository,

            PostRepository
            postRepository
    ) {

        this.likeRepository =
                likeRepository;

        this.postRepository =
                postRepository;

    }

    // =========================
    // いいね追加・解除
    // =========================
    public void toggleLike(

            int postId,
            User user
    ) {

        // 投稿取得
        Post post =
                postRepository
                .findById(postId)
                .orElse(null);

        if (post == null) {

            return;

        }

        // 既にいいね済みか確認
        Like like =
                likeRepository
                .findByUserAndPost(
                        user,
                        post
                );

        // =========================
        // 既にあるなら削除
        // =========================
        if (like != null) {

            likeRepository.delete(
                    like
            );

            return;
        }

        // =========================
        // 新規いいね
        // =========================
        Like newLike =
                new Like();

        newLike.setUser(
                user
        );

        newLike.setPost(
                post
        );

        likeRepository.save(
                newLike
        );
    }

    // =========================
    // いいね数取得
    // =========================
    public int getLikeCount(
            Post post
    ) {

        return likeRepository
                .countByPost(
                        post
                );
    }

}
