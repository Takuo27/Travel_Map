package com.takuya.travelmap.service;

// =========================
// Model
// =========================
import com.takuya.travelmap.model.Like;
import com.takuya.travelmap.model.Post;
import com.takuya.travelmap.model.User;

// =========================
// Repository
// =========================
import com.takuya.travelmap.repository.LikeRepository;
import com.takuya.travelmap.repository.PostRepository;

// =========================
// Optional
// =========================
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class LikeService {

    // =========================
    // Repository
    // =========================
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    // =========================
    // コンストラクタDI
    // =========================
    public LikeService(
            LikeRepository likeRepository,
            PostRepository postRepository
    ) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    // =========================
    // いいねON / OFF切り替え
    // =========================
    public void toggleLike(
            int postId,
            User user
    ) {

        // =========================
        // 投稿取得
        // =========================
        Post post = postRepository
                .findById(postId)
                .orElse(null);

        // 投稿が存在しない場合は終了
        if (post == null) {
            return;
        }

        // =========================
        // 既にいいねしているか確認
        // =========================
        Optional<Like> likeOpt =
                likeRepository.findByUserAndPost(user, post);

        // =========================
        // 既にいいね済み → 削除（解除）
        // =========================
        if (likeOpt.isPresent()) {

            likeRepository.delete(likeOpt.get());
            return;
        }

        // =========================
        // 未いいね → 新規作成
        // =========================
        Like newLike = new Like();

        newLike.setUser(user);
        newLike.setPost(post);

        likeRepository.save(newLike);
    }

    // =========================
    // いいね数取得
    // =========================
    public long getLikeCount(Post post) {
        return likeRepository.countByPost(post);
    }
}
