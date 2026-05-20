package com.takuya.travelmap.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comment {

    // 主キー
    @Id
    @GeneratedValue(
            strategy =
            GenerationType.IDENTITY
    )
    private int id;

    // コメント内容
    private String content;

    // 投稿日時
    private LocalDateTime createdAt;

    // 投稿との紐付け
    @ManyToOne
    @JoinColumn(
            name = "post_id"
    )
    private Post post;

    // コンストラクタ
    public Comment() {

        this.createdAt =
                LocalDateTime.now();

    }

    // Getter
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

    // Setter
    public void setContent(
            String content) {

        this.content = content;
    }

    public void setPost(
            Post post) {

        this.post = post;
    }

}
