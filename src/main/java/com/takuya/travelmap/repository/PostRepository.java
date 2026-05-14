package com.takuya.travelmap.repository;

import com.takuya.travelmap.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}