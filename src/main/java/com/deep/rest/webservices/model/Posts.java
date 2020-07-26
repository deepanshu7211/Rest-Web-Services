package com.deep.rest.webservices.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Posts {

    @Id
    @GeneratedValue
    private int id;

    private String description;

    /*
    *  @JsonIgnore is more important otherwise it will throw stackOver flow exception.
    *  User will call post and posts will call user. Recursive call.
    *  */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
