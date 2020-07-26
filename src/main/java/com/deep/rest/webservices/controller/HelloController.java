package com.deep.rest.webservices.controller;

import com.deep.rest.webservices.model.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello - World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello - World bean");
    }

    @GetMapping(path = "/hello-world/path-var/{name}")
    public HelloWorldBean helloWorldPathVar(@PathVariable String name){
        return new HelloWorldBean("Hello - World bean ," + name);
    }
}
