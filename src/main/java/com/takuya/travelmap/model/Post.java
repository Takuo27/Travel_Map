// ===============================
// Post.java
// Entity(DBテーブル)
// ===============================

package com.takuya.travelmap.model;

// JPA(Entity)関連
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// 日時クラス
import java.time.LocalDateTime;

// このクラスをDBテーブルとして扱う
@Entity
public class Post {

    // 主キー(primary key)
    @Id

    // ID自動採番
    // MySQLのAUTO_INCREMENTに対応
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 投稿内容
    private String content;

    // 緯度(latitude)
    // 例: 34.702485
    private double latitude;

    // 経度(longitude)
    // 例: 135.495951
    private double longitude;

    // 投稿日時
    private LocalDateTime createdAt;

    // デフォルトコンストラクタ
    // JPAがDBデータをJavaオブジェクトへ変換時に必要
    public Post() {
    }

    // 投稿作成用コンストラクタ
    public Post(String content,
                double latitude,
                double longitude) {

        // 投稿内容を代入
        this.content = content;

        // 緯度を代入
        this.latitude = latitude;

        // 経度を代入
        this.longitude = longitude;

        // 現在日時を設定
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

    // 投稿内容更新
    public void setContent(String content) {
        this.content = content;
    }

    // 緯度取得
    public double getLatitude() {
        return latitude;
    }

    // 経度取得
    public double getLongitude() {
        return longitude;
    }

    // 投稿日時取得
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}