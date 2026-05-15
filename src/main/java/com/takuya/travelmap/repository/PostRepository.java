package com.takuya.travelmap.repository;

// Postクラス使用
import com.takuya.travelmap.model.Post;

// JPA Repository
import org.springframework.data.jpa.repository.JpaRepository;

// Repositoryとして利用
public interface PostRepository
        extends JpaRepository<Post, Integer> {

}