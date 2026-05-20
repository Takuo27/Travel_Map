package com.takuya.travelmap.service;

import com.takuya.travelmap.model.Comment;
import com.takuya.travelmap.model.Post;

import com.takuya.travelmap.repository.CommentRepository;
import com.takuya.travelmap.repository.PostRepository;

import org.springframework.stereotype.Service;

import java.util.List;

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

    // =========================
    // コメント追加
    // =========================
    public void addComment(

            int postId,
            String content
    ) {

        // 投稿取得
        Post post =
                postRepository
                .findById(postId)
                .orElse(null);

        if(post != null){

            Comment comment =
                    new Comment();

            comment.setContent(
                    content
            );

            comment.setPost(
                    post
            );

            commentRepository.save(
                    comment
            );

        }

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

}
