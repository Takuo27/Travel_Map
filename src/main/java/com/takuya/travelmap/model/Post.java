package com.takuya.travelmap.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Post {

    // 主キー
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 投稿内容
    private String content;

    // 緯度
    private double latitude;

    // 経度
    private double longitude;

    // 投稿日時
    private LocalDateTime createdAt;

    // JPA用デフォルトコンストラクタ
    public Post() {
    }

    // 投稿生成用
    public Post(
            String content,
            double latitude,
            double longitude
    ) {

        this.content = content;

        this.latitude = latitude;

        this.longitude = longitude;

        // 現在日時
        this.createdAt = LocalDateTime.now();
    }

    // id取得
    public int getId() {
        return id;
    }

    // 投稿内容取得
    public String getContent() {
        return content;
    }

    // 投稿内容設定
    public void setContent(String content) {
        this.content = content;
    }

    // 緯度取得
    public double getLatitude() {
        return latitude;
    }

    // 緯度設定
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // 経度取得
    public double getLongitude() {
        return longitude;
    }

    // 経度設定
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // 投稿日時取得
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}