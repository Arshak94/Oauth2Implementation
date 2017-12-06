package com.example.user.demo.controller;

import com.example.user.demo.binding.UserPayload;
import com.example.user.demo.exception.InvalidRequestException;
import com.example.user.demo.model.JwtUser;
import com.example.user.demo.model.User;
import com.example.user.demo.service.JwtUserFactory;
import com.example.user.demo.service.UserService;
import com.example.user.demo.util.JwtTokenUtil;
import org.codehaus.groovy.syntax.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class UserController {

    @Value("${header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil tokenUtil;

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/test")
    public String test(){
        return "bvcvb";
    }

    @GetMapping("/")
    public JwtUser getUserById(HttpServletRequest request) throws Exception{
        String token = request.getHeader(tokenHeader).substring(7);
        JwtUser jwtUser = JwtUserFactory.create(userService.getOne(tokenUtil.getIdFromToken(token)));
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
