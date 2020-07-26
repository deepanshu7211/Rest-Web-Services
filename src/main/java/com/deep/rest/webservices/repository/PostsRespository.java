package com.deep.rest.webservices.repository;

import com.deep.rest.webservices.model.Posts;
import com.deep.rest.webservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRespository extends JpaRepository<Posts,Integer> {
}
