package com.takuya.travelmap.controller;

import com.takuya.travelmap.model.Comment;
import com.takuya.travelmap.model.Post;
import com.takuya.travelmap.model.User;

import com.takuya.travelmap.service.CommentService;
import com.takuya.travelmap.service.LikeService;
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
    private final LikeService likeService;


    // =========================
    // コンストラクタDI
    // =========================
    public PostController(
            PostService postService,
            CommentService commentService,
            LikeService likeService
        ) {
        this.postService = postService;
        this.commentService = commentService;
        this.likeService = likeService;
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

        // 未ログインならログイン画面へ
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
        // 投稿検索
        // URL: /search
        // =========================
        @GetMapping("/search")
        public String search(

                @RequestParam
                String keyword,

                Model model,

                HttpSession session
        ) {

        // ログインユーザー取得
        User loginUser =
                (User) session.getAttribute(
                        "loginUser"
                );

        // 検索結果
        model.addAttribute(
                "posts",
                postService.searchPosts(
                        keyword
                )
        );

        // 投稿フォーム用
        model.addAttribute(
                "post",
                new Post()
        );

        // ログインユーザー
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

        // ログインユーザー取得
        User loginUser =
                (User) session.getAttribute(
                        "loginUser"
                );

        post.setUser(
                loginUser
        );

        // 画像保存
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

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        // DB保存
        postService.addPost(
                post,
                imageName
        );

        return "redirect:/";
    }

    // =========================
    // 投稿削除
    // URL: /delete
    // 自分の投稿のみ削除可
    // =========================
    @PostMapping("/delete")
    public String deletePost(
            @RequestParam int id,
            HttpSession session) {

        User loginUser =
                (User) session.getAttribute(
                        "loginUser"
                );

        Post post =
                postService.getPostById(
                        id
                );

        // 他人の投稿なら削除禁止
        if (post.getUser() == null ||
            post.getUser().getId()
            != loginUser.getId()) {

            return "redirect:/";
        }

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
            @RequestParam int id,
            Model model,
            HttpSession session) {

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
                        .getCommentsByPostId(id)
        );

        model.addAttribute(
                "loginUser",
                session.getAttribute(
                        "loginUser"
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
            @RequestParam int postId,
            @RequestParam String content,
            HttpSession session) {

        User loginUser =
                (User) session.getAttribute(
                        "loginUser"
                );

        commentService.addComment(
                postId,
                content,
                loginUser
        );

        return "redirect:/detail?id="
                + postId;
    }

    // =========================
    // コメント削除
    // URL: /comment/delete
    // 自分のコメントのみ削除可
    // =========================
    @PostMapping("/comment/delete")
    public String deleteComment(
            @RequestParam int commentId,
            @RequestParam int postId,
            HttpSession session) {

        User loginUser =
                (User) session.getAttribute(
                        "loginUser"
                );

        Comment comment =
                commentService.getCommentById(
                        commentId
                );

        // 他人コメント削除禁止
        if (comment.getUser() == null ||
            comment.getUser().getId()
            != loginUser.getId()) {

            return "redirect:/detail?id="
                    + postId;
        }

        commentService.deleteComment(
                commentId
        );

        return "redirect:/detail?id="
                + postId;
    }

    // =========================
    // 編集画面
    // URL: /edit
    // 自分の投稿のみ編集可
    // =========================
    @GetMapping("/edit")
    public String edit(
            @RequestParam int id,
            Model model,
            HttpSession session) {

        User loginUser =
                (User) session.getAttribute(
                        "loginUser"
                );

        Post post =
                postService.getPostById(
                        id
                );

        // 他人投稿編集禁止
        if (post.getUser() == null ||
            post.getUser().getId()
            != loginUser.getId()) {

            return "redirect:/";
        }

        model.addAttribute(
                "post",
                post
        );

        return "edit";
    }

        // =========================
        // 投稿更新
        // URL: /update
        // 自分の投稿のみ編集可
        // =========================
        @PostMapping("/update")
        public String updatePost(

                @ModelAttribute
                Post updatePost,

                @RequestParam("image")
                MultipartFile image,

                HttpSession session
        ) {

        // =========================
        // ログインユーザー取得
        // =========================
        User loginUser =
                (User) session.getAttribute(
                        "loginUser"
                );

        // =========================
        // DBから投稿取得
        // =========================
        Post post =
                postService.getPostById(
                        updatePost.getId()
                );

        // =========================
        // 他人の投稿なら編集不可
        // =========================
        if (post.getUser() == null ||
                post.getUser().getId()
                != loginUser.getId()) {

                return "redirect:/";
        }

        // =========================
        // 投稿内容更新
        // =========================
        post.setContent(
                updatePost.getContent()
        );

        // =========================
        // 緯度経度保持
        // =========================
        post.setLatitude(
                updatePost.getLatitude()
        );

        post.setLongitude(
                updatePost.getLongitude()
        );

        // =========================
        // 新画像アップロード
        // =========================
        if (!image.isEmpty()) {

                try {

                String imageName =
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

                post.setImagePath(
                        imageName
                );

                } catch (Exception e) {

                e.printStackTrace();

                }
        }

        // =========================
        // 更新保存
        // =========================
        postService.save(
                post
        );

        return "redirect:/";
        }

        // =========================
        // いいね追加/解除
        // URL: /like
        // =========================
        @PostMapping("/like")
        public String like(

                @RequestParam
                int postId,

                HttpSession session
        ) {

        // ログインユーザー取得
        User loginUser =
                (User) session.getAttribute(
                        "loginUser"
                );

        // 未ログインなら戻す
        if (loginUser == null) {

                return "redirect:/login";
        }

        // いいね切替
        likeService.toggleLike(

                postId,
                loginUser
        );

        return "redirect:/";
        }
}