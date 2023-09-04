package com.samarth.JWTExample3.Controllers;
import com.samarth.JWTExample3.Service.UserService;
import com.samarth.JWTExample3.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;



@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    // url --> http://localhost:9091/home/user
    @GetMapping("/users")
    public List<User> getUser(){
        System.out.println("Getting Users");
        return this.userService.getUser();
    }

    @GetMapping("/currentUser")
    public String getLoggedInUser(Principal principal){
        return principal.getName();  // will return the current logged in user
    }
}
