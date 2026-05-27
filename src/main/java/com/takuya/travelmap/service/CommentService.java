package com.takuya.travelmap.service;

import com.takuya.travelmap.model.Comment;
import com.takuya.travelmap.model.Post;

import com.takuya.travelmap.repository.CommentRepository;
import com.takuya.travelmap.repository.PostRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import com.takuya.travelmap.model.User;
import com.takuya.travelmap.model.Post;
import com.takuya.travelmap.model.Comment;

@Service
public class CommentService {

    // Repository
    private final CommentRepository
            commentRepository;

    private final PostRepository
            postRepository;

    // コンストラクタDI
    public CommentService(

            CommentRepository
            commentRepository,

            PostRepository
            postRepository
    ) {

        this.commentRepository =
                commentRepository;

        this.postRepository =
                postRepository;
    }

    public void addComment(
            int postId,
            String content,
            User user
    ) {

        Post post =
                postRepository
                .findById(postId)
                .orElse(null);

        Comment comment =
                new Comment();

        comment.setContent(
                content
        );

        comment.setPost(
                post
        );

        comment.setUser(
                user
        );

        commentRepository.save(
                comment
        );
    }

    // =========================
    // コメント一覧取得
    // =========================
    public List<Comment>
    getCommentsByPostId(

            int postId
    ){

        return commentRepository
                .findByPostId(
                        postId
                );
    }

    // =========================
    // コメント削除
    // =========================
    public void deleteComment(
            int id
    ) {
        commentRepository
                .deleteById(id);
    }

    // =========================
    // コメント取得
    // ID指定
    // =========================
    public Comment getCommentById(
            int id
    ) {

        return commentRepository
                .findById(id)
                .orElse(null);
    }
}
