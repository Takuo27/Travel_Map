// ===============================
// PostController.java
// Controller(URL受付)
// ===============================

package com.takuya.travelmap.controller;

// Serviceクラス読み込み
import com.takuya.travelmap.service.PostService;

// Spring MVC関連
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

// HTTPリクエスト関連
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// このクラスを「Controller」としてSpringへ登録
// Controller = URLを受け取り画面(html)を返す役割
@Controller
public class PostController {

    // Serviceクラスを保持
    // final = 一度代入したら変更不可
    private final PostService postService;

    // コンストラクタ
    // Springが自動でPostServiceを渡してくれる(DI)
    public PostController(PostService postService) {

        // 引数のpostServiceを
        // メンバ変数へ代入
        this.postService = postService;
    }

    // ========================================
    // TOP画面表示
    // URL: http://localhost:8080/
    // GET通信
    // ========================================
    @GetMapping("/")
    public String index(Model model) {

        // 投稿一覧をDBから取得
        var posts = postService.getPosts();

        // HTMLへデータ受け渡し
        // "posts" という名前で渡す
        model.addAttribute("posts", posts);

        // templates/index.html を表示
        return "index";
    }

    // ========================================
    // 投稿追加処理
    // URL: /posts
    // POST通信
    // ========================================
    @PostMapping("/posts")
    public String addPost(

            // formのcontent値受け取り
            @RequestParam String content,

            // formのlatitude値受け取り
            @RequestParam double latitude,

            // formのlongitude値受け取り
            @RequestParam double longitude) {

        // Serviceへ投稿保存処理依頼
        postService.addPost(content,
                            latitude,
                            longitude);

        // 投稿後TOP画面へ戻る
        // redirect: = リダイレクト
        return "redirect:/";
    }
}