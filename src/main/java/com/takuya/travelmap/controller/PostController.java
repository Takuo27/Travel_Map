package com.takuya.travelmap.controller;

import com.takuya.travelmap.model.Post;
import com.takuya.travelmap.model.User;
import com.takuya.travelmap.service.CommentService;
import com.takuya.travelmap.service.PostService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class PostController {

    // =========================
    // Service
    // =========================
    private final PostService postService;
    private final CommentService commentService;

    // =========================
    // コンストラクタDI
    // =========================
    public PostController(
            PostService postService,
            CommentService commentService) {

        this.postService = postService;
        this.commentService = commentService;
    }

    // =========================
    // TOP画面
    // URL: /
    // =========================
    @GetMapping("/")
    public String index(
            Model model,
            HttpSession session) {

        Object loginUser =
                session.getAttribute(
                        "loginUser"
                );

        // 未ログインならログイン画面
        if (loginUser == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "posts",
                postService.getPosts()
        );

        model.addAttribute(
                "post",
                new Post()
        );

        model.addAttribute(
                "loginUser",
                loginUser
        );

        return "index";
    }

    // =========================
    // 投稿処理
    // URL: /posts
    // =========================
    @PostMapping("/posts")
    public String addPost(

            @Valid
            @ModelAttribute
            Post post,

            BindingResult result,

            @RequestParam("image")
            MultipartFile image,

            Model model,

            HttpSession session
    ) {

        // 入力エラー
        if (result.hasErrors()) {

            model.addAttribute(
                    "posts",
                    postService.getPosts()
            );

            return "index";
        }

        // =========================
        // ログインユーザー取得
        // =========================
        User loginUser =
                (User) session.getAttribute(
                        "loginUser"
                );

        post.setUser(
                loginUser
        );

        // =========================
        // 画像保存
        // =========================
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

                        StandardCopyOption
                                .REPLACE_EXISTING
                );

                System.out.println(
                        "保存完了 : "
                        + imageName
                );

            } catch (Exception e) {

                e.printStackTrace();

            }
        }

        // =========================
        // DB保存
        // =========================
        postService.addPost(
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
            @RequestParam int id) {

        postService.deletePost(
                id
        );

        return "redirect:/";
    }

    // =========================
    // 詳細画面
    // URL: /detail
    // =========================
    @GetMapping("/detail")
    public String detail(

            @RequestParam
            int id,

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
    @PostMapping("/comment/delete")
    public String deleteComment(

            @RequestParam
            int commentId,

            @RequestParam
            int postId
    ) {

        commentService.deleteComment(
                commentId
        );

        return "redirect:/detail?id="
                + postId;
    }
}