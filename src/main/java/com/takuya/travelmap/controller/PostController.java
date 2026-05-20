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

import com.takuya.travelmap.service.CommentService;

// ファイル操作
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// ファイルコピー
import java.nio.file.StandardCopyOption;

@Controller
public class PostController {

    // =========================
    // Service保持
    // =========================
    private final PostService postService;

    private final CommentService commentService;

    // =========================
    // コンストラクタDI
    // =========================
        public PostController(

                PostService postService,

                CommentService commentService
        ) {

        this.postService =
                postService;

        this.commentService =
                commentService;
        }

    // =========================
    // TOP画面表示
    // URL: /
    // =========================
    @GetMapping("/")
    public String index(
            Model model) {

        // 投稿一覧
        model.addAttribute(
                "posts",
                postService.getPosts()
        );

        // 投稿フォーム用
        model.addAttribute(
                "post",
                new Post()
        );

        // 投稿件数
        model.addAttribute(
                "postCount",
                postService.getPostCount()
        );

        model.addAttribute(
                "placeCount",
                postService.getPlaceCount()
        );
        return "index";
    }

    // =========================
    // 投稿追加
    // URL: /posts
    // =========================
    @PostMapping("/posts")
    public String addPost(

            @Valid
            @ModelAttribute Post post,

            BindingResult result,

            @RequestParam("image")
            MultipartFile image,

            Model model
    ) {

        // 入力エラー
        if (result.hasErrors()) {

            model.addAttribute(
                    "posts",
                    postService.getPosts()
            );

            model.addAttribute(
                    "postCount",
                    postService.getPostCount()
            );

            return "index";
        }

        String imageName = "";

        // =========================
        // 画像保存
        // =========================
        if (!image.isEmpty()) {

            try {

                imageName =
                        image.getOriginalFilename();

                Path uploadPath =
                        Paths.get(
                                "src/main/resources/static/uploads"
                        );

                Files.copy(
                        image.getInputStream(),
                        uploadPath.resolve(
                                imageName
                        ),
                        StandardCopyOption.REPLACE_EXISTING
                );

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        // DB保存
        postService.addPost(
                post.getContent(),
                post.getLatitude(),
                post.getLongitude(),
                imageName
        );

        return "redirect:/";
    }

    // =========================
    // 編集画面表示
    // URL: /edit
    // =========================
    @GetMapping("/edit")
    public String editPost(

            @RequestParam int id,

            Model model
    ) {

        Post post =
                postService.getPostById(
                        id
                );

        model.addAttribute(
                "post",
                post
        );

        return "edit";
    }

    // =========================
    // 更新処理
    // URL: /update
    // =========================
    @PostMapping("/update")
    public String updatePost(

            @ModelAttribute Post post,

            @RequestParam("image")
            MultipartFile image
    ) {

        String imageName = "";

        if (!image.isEmpty()) {

            try {

                imageName =
                        image.getOriginalFilename();

                Path uploadPath =
                        Paths.get(
                                "src/main/resources/static/uploads"
                        );

                Files.copy(
                        image.getInputStream(),
                        uploadPath.resolve(
                                imageName
                        ),
                        StandardCopyOption.REPLACE_EXISTING
                );

            } catch(Exception e){

                e.printStackTrace();

            }

        }

        postService.updatePost(
                post,
                imageName
        );

        return "redirect:/";
    }

    // =========================
    // 投稿削除
    // URL: /delete
    // =========================
    @PostMapping("/delete")
    public String deletePost(

            @RequestParam int id
    ) {

        postService.deletePost(
                id
        );

        return "redirect:/";
    }

    // =========================
    // 投稿検索
    // URL: /search
    // =========================
    @GetMapping("/search")
    public String searchPosts(

            @RequestParam
            String keyword,

            Model model
    ) {

        model.addAttribute(
                "posts",
                postService.searchPosts(
                        keyword
                )
        );

        model.addAttribute(
                "post",
                new Post()
        );

        model.addAttribute(
                "keyword",
                keyword
        );

        model.addAttribute(
                "postCount",
                postService.getPostCount()
        );

        model.addAttribute(
                "placeCount",
                postService.getPlaceCount()
        );
        return "index";
    }

        // =========================
        // 投稿詳細画面
        // URL: /detail
        // =========================
        @GetMapping("/detail")
        public String detailPost(

                @RequestParam
                int id,

                Model model
        ) {

        // IDから投稿取得
        Post post =
                postService.getPostById(
                        id
                );

        // HTMLへ渡す
        model.addAttribute(
                "post",
                post
        );

        model.addAttribute(
                "comments",
                commentService
                .getCommentsByPostId(
                        id
                )
        );

        return "detail";
        }

        // =========================
        // いいね処理
        // URL: /like
        // =========================
        @PostMapping("/like")
        public String addLike(

                @RequestParam
                int id
        ) {

        // いいね追加
        postService.addLike(
                id
        );

        // 詳細画面へ戻る
        return "redirect:/detail?id="
                + id;

        }

        // =========================
        // コメント追加
        // URL: /comment
        // =========================
        @PostMapping("/comment")
        public String addComment(

                @RequestParam
                int postId,

                @RequestParam
                String content
        ) {

        commentService.addComment(

                postId,
                content
        );

        return "redirect:/detail?id="
                + postId;

        }

        // =========================
        // コメント削除
        // URL: /comment/delete
        // =========================
        @PostMapping(
                "/comment/delete"
        )
        public String deleteComment(

                @RequestParam
                int commentId,

                @RequestParam
                int postId
        ) {

        commentService
                .deleteComment(
                        commentId
                );

        return
                "redirect:/detail?id="
                + postId;
        }
}