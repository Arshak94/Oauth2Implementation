package com.example.user.demo.controller;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.exception.InvalidRequestException;
import com.example.user.demo.model.JwtUser;
import com.example.user.demo.model.User;
import com.example.user.demo.service.JwtUserFactory;
import com.example.user.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public JwtUser getUserById(@PathVariable Long id) throws Exception{
        JwtUser jwtUser = JwtUserFactory.create(userService.getOne(id));
        return jwtUser;
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<JwtUser> getAllUsers() throws Exception{
        List<JwtUser> jwtUsers = new ArrayList<>();
        for (User user :
                userService.getAll()) {
            jwtUsers.add(JwtUserFactory.create(user));
        }

        return jwtUsers;
    }

    @PutMapping("/{id}")
    public JwtUser update(@PathVariable Long id,
                       @Valid @RequestBody UserPayload userPayload,
                       BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            throw new InvalidRequestException("validation issues");
        }
        return JwtUserFactory.create(userService.update(id,userPayload));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

}
