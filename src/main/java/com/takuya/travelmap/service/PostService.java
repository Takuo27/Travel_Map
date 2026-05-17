// ===============================
// PostService.java
// 業務処理(Service層)
// ===============================

package com.takuya.travelmap.service;

import com.takuya.travelmap.model.Post;
import com.takuya.travelmap.repository.PostRepository;

import org.springframework.stereotype.Service;

import java.util.List;

// ServiceクラスとしてSpring管理対象
@Service
public class PostService {

    // Repository保持
    private final PostRepository postRepository;

    // コンストラクタDI
    public PostService(PostRepository postRepository) {

        this.postRepository = postRepository;
    }

    // 投稿一覧取得
    public List<Post> getPosts() {

        // DBから全件取得
        return postRepository.findAll();
    }

    // ===============================
    // 投稿追加
    // ===============================
    public void addPost(String content,
                        double latitude,
                        double longitude,
                        String imagePath) {

        // Post生成
        Post post = new Post(
                content,
                latitude,
                longitude
        );

        // 画像ファイル名セット
        post.setImagePath(
                imagePath
        );

        // DB保存
        postRepository.save(
                post
        );
    }

    // ===============================
    // 投稿削除
    // ===============================
    public void deletePost(int id) {

        // idを指定して削除
        postRepository.deleteById(id);

    }
}
