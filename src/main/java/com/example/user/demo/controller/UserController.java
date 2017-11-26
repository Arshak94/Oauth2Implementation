package com.example.user.demo.controller;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.exception.InvalidRequestException;
import com.example.user.demo.model.User;
import com.example.user.demo.projection.PublicUserProjection;
import com.example.user.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class UserController {

    private UserService userService;
    /*private ProjectionFactory projectionFactory;*/

    @Autowired
    public UserController(UserService userService/*, ProjectionFactory projectionFactory*/){
        this.userService = userService;
        /*this.projectionFactory = projectionFactory;*/
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) throws Exception{
        return userService.getOne(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() throws Exception{
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Integer id,
                       @Valid @RequestBody UserPayload userPayload,
                       BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            throw new InvalidRequestException("validation issues");
        }
        return userService.update(id,userPayload);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }

}
