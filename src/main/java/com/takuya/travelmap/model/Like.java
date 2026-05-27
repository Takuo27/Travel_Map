package com.takuya.travelmap.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "likes"
)
public class Like {

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
    // ユーザー
    // =========================
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    // =========================
    // 投稿
    // =========================
    @ManyToOne
    @JoinColumn(
            name = "post_id"
    )
    private Post post;

    // =========================
    // Getter
    // =========================
    public int getId() {

        return id;
    }

    public User getUser() {

        return user;
    }

    public Post getPost() {

        return post;
    }

    // =========================
    // Setter
    // =========================
    public void setUser(
            User user
    ) {

        this.user =
                user;
    }

    public void setPost(
            Post post
    ) {

        this.post =
                post;
    }

}
