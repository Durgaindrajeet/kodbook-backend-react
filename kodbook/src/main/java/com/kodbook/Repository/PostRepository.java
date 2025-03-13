package com.kodbook.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodbook.Entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {

}
