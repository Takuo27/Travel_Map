package com.takuya.travelmap.model;

// DBテーブル関連
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
// 入力チェック
import jakarta.validation.constraints.NotBlank;

// 日時
import java.time.LocalDateTime;

@Entity
public class Post {

    // 主キー
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 投稿内容
    @NotBlank(message = "投稿内容を入力してください")
    private String content;

    // 緯度
    private double latitude;

    // 経度
    private double longitude;

    // 投稿日時
    private LocalDateTime createdAt;

    // 画像パス
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // いいね数
    private int likes;

    // =========================
    // いいね数
    // DB保存対象外
    // =========================
    @Transient
    private int likeCount;

    // 空コンストラクタ
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
        this.createdAt = LocalDateTime.now();

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

    public double getLatitude() {

        return latitude;
    }

    public double getLongitude() {

        return longitude;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public String getImagePath() {

        return imagePath;
    }

    public int getLikes() {

        return likes;
    }

    public User getUser() {
        return user;
    }

    public int getLikeCount() {
        return likeCount;
    }

    // =========================
    // Setter
    // =========================

    public void setId(
            int id
    ) {

        this.id = id;
    }

    public void setContent(
            String content
    ) {

        this.content = content;
    }

    public void setLatitude(
            double latitude
    ) {

        this.latitude = latitude;
    }

    public void setLongitude(
            double longitude
    ) {

        this.longitude = longitude;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {

        this.createdAt = createdAt;
    }

    public void setImagePath(
            String imagePath
    ) {

        this.imagePath = imagePath;
    }
    public void setLikes(
            int likes) {

        this.likes = likes;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLikeCount(
        int likeCount
    ) {
        this.likeCount =
                likeCount;
    }
}