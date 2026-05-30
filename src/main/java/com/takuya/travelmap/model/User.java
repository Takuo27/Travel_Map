package com.takuya.travelmap.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    // =========================
    // 主キー
    // =========================
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    // =========================
    // ログインID
    // unique=true
    // 同じユーザー名登録禁止
    // =========================
    @Column(unique = true)
    private String username;

    // =========================
    // パスワード
    // =========================
    private String password;

    // =========================
    // 表示名
    // =========================
    private String name;

    // =========================
    // JPA用空コンストラクタ
    // =========================
    public User() {

    }

    // =========================
    // Getter
    // =========================
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // =========================
    // Setter
    // =========================
    public void setUsername(
            String username
    ) {
        this.username = username;
    }

    public void setPassword(
            String password
    ) {
        this.password = password;
    }

    public void setName(
            String name
    ) {
        this.name = name;
    }
}
