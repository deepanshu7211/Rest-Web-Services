package com.deep.rest.webservices.controller;

import com.deep.rest.webservices.exception.UserNotFoundException;
import com.deep.rest.webservices.model.User;
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

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
            return userDaoService.getAllUsers();
    }

    @GetMapping("users/{id}")
    public EntityModel<User> getUser(@PathVariable int id){
        User user = userDaoService.findUser(id);
        if(user==null){
            throw  new UserNotFoundException(" id " + id);
        }

//        Below thre Lines for Hateoas Implementation
        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
        resource.add(linkTo.withRel("all-users"));

//        return ResponseEntity.ok(resource);

        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
//        return userDaoService.save(user);
      User savedUser = userDaoService.save(user);
      URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

//      new ResponseEntity<>(user, HttpStatus.CREATED); This can be also used
      return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id){
        User user = userDaoService.deleteUser(id);
        if(user==null){
            throw  new UserNotFoundException(" id " + id);
        }

        return new ResponseEntity<>(user,HttpStatus.NO_CONTENT);
    }

}
