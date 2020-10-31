package com.oracle.mybatis.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import com.oracle.mybatis.domain.UserDO;
import com.oracle.mybatis.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
 
    @Autowired
    private UserService userService;
 
    /**
           * The test parameter is name
     * */
    @RequestMapping("/hello")
    public String hello(String name){
        return "Hello "+name;
    }
 
    /**
           * SELECT query operation, returns a JSON array
           * Is idempotent
     * */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Object getUsers(){
 
        return userService.list(new HashMap<>());
    }
 
    /**
           * SELECT query operation returns a new JSON object
           * Is idempotent
     * */
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getUser(@PathVariable("id") String id){
 
        if(null==id){
            return  null;
        }
        return userService.get(Integer.valueOf(id));
    }
 
    /**
           * Add a user object
           * Non-idempotent
           * Return 201 HttpStatus.CREATED in response to the POST operation to create a new resource. Should carry the Location header pointing to the new resource address
     * */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addUser(@RequestBody UserDO user){
        if(user.getId()==0){
            return null;
        }
        userService.save(user);
        return user;
    }
 
    /**
           * Edit a user object
           * Idempotence
     * */
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Object editUser(@PathVariable("id") String id, @RequestBody UserDO user){
 
        userService.update(user);
        return user;
    }
 
    /**
           * Delete a user object
           * Idempotence
           * Return HttpStatus.NO_CONTENT means no return content
     * */
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") String id){
        userService.remove(Integer.valueOf(id));
    }
}