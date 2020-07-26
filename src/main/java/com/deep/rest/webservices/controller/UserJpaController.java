package com.deep.rest.webservices.controller;

import com.deep.rest.webservices.exception.UserNotFoundException;
import com.deep.rest.webservices.model.Posts;
import com.deep.rest.webservices.model.User;
import com.deep.rest.webservices.repository.PostsRespository;
import com.deep.rest.webservices.repository.UserRespository;
import com.deep.rest.webservices.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/*
* This is Example for JPA Controller for getting the data from DB H2
* */

@RestController
public class UserJpaController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private PostsRespository postsRespository;


    @GetMapping("/jpa/users")
    public List<User> getAllUsers(){
            return userRespository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> getUser(@PathVariable int id){
        Optional<User> user = userRespository.findById(id);
        if(!user.isPresent()){
            throw  new UserNotFoundException(" id " + id);
        }

//        Below thre Lines for Hateoas Implementation
        EntityModel<User> resource = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
        resource.add(linkTo.withRel("all-users"));

//        return ResponseEntity.ok(resource);

        return resource;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
      User savedUser = userRespository.save(user);
      URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

//      new ResponseEntity<>(user, HttpStatus.CREATED); This can be also used
      return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){
         userRespository.deleteById(id);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/jpa/users/{id}/posts")
    public List<Posts> getAllUserPosts(@PathVariable("id") int userId){

        Optional<User> user = userRespository.findById(userId);
        if(!user.isPresent()){
            throw  new UserNotFoundException(" id " + userId);
        }

        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createUserPost(@PathVariable int id, @RequestBody Posts posts){
        Optional<User> user = userRespository.findById(id);
        if(!user.isPresent()){
            throw  new UserNotFoundException(" id " + id);
        }

        User user1 = user.get();
        posts.setUser(user1);

        postsRespository.save(posts);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(posts.getId())
                .toUri();

//      new ResponseEntity<>(user, HttpStatus.CREATED); This can be also used
        return ResponseEntity.created(location).build();
    }
}
