package com.takuya.travelmap.service;

import com.takuya.travelmap.model.Post;
import com.takuya.travelmap.repository.PostRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(
            PostRepository postRepository
    ) {

        this.postRepository = postRepository;
    }

    // =========================
    // 全件取得
    // =========================
    public List<Post> getPosts() {

        return postRepository.findAll();
    }

    // =========================
    // 投稿追加
    // =========================
    public void addPost(

            String content,
            double latitude,
            double longitude,
            String imagePath

    ) {

        Post post =
                new Post(
                        content,
                        latitude,
                        longitude
                );

        post.setImagePath(
                imagePath
        );

        post.setCreatedAt(
                LocalDateTime.now()
        );

        postRepository.save(
                post
        );

    }

    // =========================
    // 投稿削除
    // =========================
    public void deletePost(
            int id
    ) {

        postRepository.deleteById(
                id
        );

    }

    // =========================
    // ID取得
    // =========================
    public Post getPostById(
            int id
    ) {

        return postRepository
                .findById(id)
                .orElse(null);

    }

    // =========================
    // 投稿更新
    // =========================
    public void updatePost(
            Post post,
            String imageName
    ) {

        Post oldPost =
                postRepository
                .findById(
                        post.getId()
                )
                .orElse(null);

        if(oldPost != null){

            oldPost.setContent(
                    post.getContent()
            );

            oldPost.setLatitude(
                    post.getLatitude()
            );

            oldPost.setLongitude(
                    post.getLongitude()
            );

            // 新画像がある場合のみ更新
            if(!imageName.isEmpty()){

                oldPost.setImagePath(
                        imageName
                );

            }

            postRepository.save(
                    oldPost
            );

        }

    }

    // =========================
    // キーワード検索
    // =========================
    public List<Post> searchPosts(
            String keyword
    ) {

        return postRepository
                .findByContentContaining(
                        keyword
                );

    }

    // =========================
    // 投稿件数取得
    // =========================
    public long getPostCount() {
        return postRepository.count();
    }

    // =========================
    // 訪問場所数取得
    // =========================
    public long getPlaceCount() {
        return postRepository
                .countDistinctContent();
    }

        // =========================
        // いいね追加
        // =========================
        public void addLike(
                int id
        ) {

        // IDから投稿取得
        Post post =
                postRepository
                .findById(id)
                .orElse(null);

        // 投稿存在時
        if(post != null){

                // いいね +1
                post.setLikes(
                        post.getLikes() + 1
                );

                // DB更新
                postRepository.save(
                        post
                );
        }
}
}