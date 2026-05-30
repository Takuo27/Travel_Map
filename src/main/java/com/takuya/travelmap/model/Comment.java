package com.takuya.travelmap.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    // =========================
    // 主キー
    // =========================
    @Id
    @GeneratedValue(
            strategy =
            GenerationType.IDENTITY
    )
    private int id;

    // =========================
    // コメント内容
    // =========================
    private String content;

    // =========================
    // 投稿日時
    // =========================
    private LocalDateTime createdAt;

    // =========================
    // 投稿との紐付け
    // (複数コメント → 1投稿)
    // =========================
    @ManyToOne
    @JoinColumn(
            name = "post_id"
    )
    private Post post;

    // =========================
    // ユーザーとの紐付け
    // (複数コメント → 1ユーザー)
    // =========================
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    // =========================
    // コンストラクタ
    // =========================
    public Comment() {

        this.createdAt =
                LocalDateTime.now();

    }

    // =========================
    // Getter
    // =========================
    public int getId() {

        return id;
    }

    public String getContent() {

        return content;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public Post getPost() {

        return post;
    }

    public User getUser() {

        return user;
    }

    // =========================
    // Setter
    // =========================
    public void setContent(
            String content
    ) {

        this.content = content;

    }

    public void setPost(
            Post post
    ) {

        this.post = post;

    }

    public void setUser(
            User user
    ) {

        this.user = user;

    }

}
