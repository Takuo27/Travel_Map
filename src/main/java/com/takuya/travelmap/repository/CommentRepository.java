package com.takuya.travelmap.repository;

import com.takuya.travelmap.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<
        Comment,
        Integer> {

    // 投稿IDでコメント取得
    List<Comment> findByPostId(
            int postId
    );

}
