package com.takuya.travelmap.controller;

import com.takuya.travelmap.service.PostService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    // Service保持
    private final PostService postService;

    // コンストラクタDI
    public PostController(PostService postService) {

        this.postService = postService;
    }

    // TOP画面表示
    @GetMapping("/")
    public String index(Model model) {

        // DBから投稿一覧取得
        var posts = postService.getPosts();

        // HTMLへ渡す
        model.addAttribute("posts", posts);

        // index.html表示
        return "index";
    }

    // 投稿保存
    @PostMapping("/posts")
    public String addPost(

            // 投稿内容
            @RequestParam String content,

            // 緯度
            @RequestParam double latitude,

            // 経度
            @RequestParam double longitude
    ) {

        // Serviceへ保存依頼
        postService.addPost(

                content,
                latitude,
                longitude
        );

        // TOPへリダイレクト
        return "redirect:/";
    }
}