package com.takuya.travelmap.model;

// DBテーブル関連
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// 入力チェック(バリデーション)
import jakarta.validation.constraints.NotBlank;

// 日時クラス
import java.time.LocalDateTime;

// EntityとしてSpring/JPAへ登録
// → このクラスがDBテーブルになる
@Entity
public class Post {

    // 主キー(id)
    @Id

    // 自動採番設定
    // IDENTITY：
    // DB側で 1,2,3... と自動採番
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 投稿内容
    // NotBlank：
    // ・null禁止
    // ・空文字禁止
    // ・スペースのみ禁止
    @NotBlank(message = "投稿内容を入力してください")
    private String content;

    // 緯度
    private double latitude;

    // 経度
    private double longitude;

    // 投稿日時
    private LocalDateTime createdAt;

    // 画像ファイル名
    private String imagePath;

    // JPA用の空コンストラクタ
    // Entityでは必須
    public Post() {

    }

    // 投稿生成用コンストラクタ
    public Post(String content,
                double latitude,
                double longitude) {

        // 引数の投稿内容をセット
        this.content = content;

        // 引数の緯度セット
        this.latitude = latitude;

        // 引数の経度セット
        this.longitude = longitude;

        // 投稿作成時の現在日時セット
        this.createdAt = LocalDateTime.now();
    }

    // =========================
    // Getter
    // =========================

    // id取得
    public int getId() {

        return id;
    }

    // 投稿内容取得
    public String getContent() {

        return content;
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

    // =========================
    // Setter
    // =========================

    // 投稿内容変更
    public void setContent(String content) {

        this.content = content;
    }

    // =========================
    // 画像取得
    // =========================
    public String getImagePath() {

        return imagePath;
    }

    // =========================
    // 画像設定
    // =========================
    public void setImagePath(
            String imagePath) {

        this.imagePath = imagePath;
    }
}