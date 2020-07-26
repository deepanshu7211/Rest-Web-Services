package com.deep.rest.webservices.service;

import com.deep.rest.webservices.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoService {
    private static List<User> userList = new ArrayList<>();
    static int count=3;

    static {
        userList.add(new User(1,"Adam",new Date()));
        userList.add(new User(2,"Eve",new Date()));
        userList.add(new User(3,"Jack",new Date()));
    }

    public List<User>  getAllUsers(){
        return userList;
    }

    public User save(User user){
        if(user.getId()==null){
            user.setId(++count);
        }
        userList.add(user);
        return user;
    }

    public User findUser(int id){
        Optional<User> optionalUser = userList.stream()
                .filter(user -> user.getId()==id)
                .findFirst();
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }

        return null;
    }

    public User deleteUser(int id){
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if(user.getId()==id){
                iterator.remove();
                return user;
            }
        }

        return null;
    }
}
