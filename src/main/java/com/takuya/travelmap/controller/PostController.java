package com.takuya.travelmap.controller;

// モデル(Entity)
import com.takuya.travelmap.model.Post;

// Service
import com.takuya.travelmap.service.PostService;

// 入力チェック
import jakarta.validation.Valid;

// Spring MVC
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

// バリデーション結果保持
import org.springframework.validation.BindingResult;

// URLマッピング
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// フォームオブジェクト受取
import org.springframework.web.bind.annotation.ModelAttribute;

// リクエストパラメータ取得
import org.springframework.web.bind.annotation.RequestParam;

// ファイルアップロード受取
import org.springframework.web.multipart.MultipartFile;

// ファイル操作
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// ファイルコピー
import java.nio.file.StandardCopyOption;

// ControllerとしてSpring管理
@Controller
public class PostController {

    // =========================
    // Service保持
    // =========================
    private final PostService postService;

    // =========================
    // コンストラクタDI
    // Springが自動注入
    // =========================
    public PostController(
            PostService postService) {

        this.postService = postService;
    }

    // =========================
    // TOP画面表示
    // URL:
    // /
    // =========================

    @GetMapping("/")
    public String index(Model model) {

        // DB投稿一覧取得
        model.addAttribute(
                "posts",
                postService.getPosts()
        );

        // 空Post生成
        // フォーム用
        model.addAttribute(
                "post",
                new Post()
        );

        // templates/index.html表示
        return "index";
    }

    // =========================
    // 投稿処理
    // URL:
    // /posts
    // =========================

    @PostMapping("/posts")
    public String addPost(

            // 入力チェック実行
            @Valid

            // フォーム入力値をPostへ格納
            @ModelAttribute Post post,

            // エラー保持
            BindingResult result,

            // 画像ファイル受取
            @RequestParam("image")
            MultipartFile image,

            Model model
    ) {

        // =========================
        // 入力エラー時
        // =========================

        if (result.hasErrors()) {

            // 投稿一覧再取得
            model.addAttribute(
                    "posts",
                    postService.getPosts()
            );

            return "index";
        }

        // =========================
        // 画像保存
        // =========================

        // 保存する画像名
        String imageName = "";

        if (!image.isEmpty()) {

            try {

                // 元画像名取得
                imageName =
                    image.getOriginalFilename();

                // 保存先
                Path uploadPath =
                    Paths.get(
                        "src/main/resources/static/uploads"
                    );

                // ファイル保存
                Files.copy(

                    image.getInputStream(),

                    uploadPath.resolve(
                        imageName
                    ),

                    StandardCopyOption.REPLACE_EXISTING

                );

                System.out.println(

                    "保存完了: " +
                    imageName

                );

            }

            catch(Exception e){

                e.printStackTrace();

            }

        }

        // =========================
        // DB保存
        // =========================
        postService.addPost(
                post.getContent(),
                post.getLatitude(),
                post.getLongitude(),
                imageName
        );

        // TOPへ戻る
        return "redirect:/";
    }

    // =========================
    // 投稿削除
    // URL:
    // /delete
    // =========================

    @PostMapping("/delete")
    public String deletePost(

            // 削除対象ID受取
            @RequestParam int id
    ) {

        // Service呼出
        postService.deletePost(id);

        // TOPへ戻る
        return "redirect:/";
    }

}